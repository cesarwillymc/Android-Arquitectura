package com.doapps.android.weatherapp.repo

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.doapps.android.weatherapp.core.Constants
import com.doapps.android.conexionmodule.db.room.entity.CurrentWeatherEntity
import com.doapps.android.domain.datasource.currentWeather.CurrentWeatherLocalDataSource
import com.doapps.android.domain.datasource.currentWeather.CurrentWeatherRemoteDataSource
import com.doapps.android.domain.repo.currentweather.CurrentWeatherRepositoryImpl
import com.doapps.android.domain.utils.status.Resource
import com.doapps.android.domain.utils.status.Status
import com.doapps.android.weatherapp.util.createSampleCurrentWeatherResponse
import com.doapps.android.weatherapp.util.generateCurrentWeatherEntity
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.annotation.Config
import retrofit2.Response


@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class CurrentWeatherRepositoryTest : KoinTest {

    @MockK
    lateinit var currentWeatherRemoteDataSource: CurrentWeatherRemoteDataSource

    @MockK
    lateinit var currentWeatherLocalDataSource: CurrentWeatherLocalDataSource

    private lateinit var currentWeatherRepository: CurrentWeatherRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        currentWeatherRepository =
            CurrentWeatherRepositoryImpl(currentWeatherRemoteDataSource, currentWeatherLocalDataSource)
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `given fetchRequired = false, when getCurrentWeather called, then make sure db called`() {

        // Given
        val fetchRequired = false
        val lat = 30.0
        val lon = 34.0
        val currentWeatherLiveData: MutableLiveData<CurrentWeatherEntity> = MutableLiveData()
        currentWeatherLiveData.postValue(generateCurrentWeatherEntity("Istanbul", 1))


        // When
        every { currentWeatherLocalDataSource.getCurrentWeather() } returns currentWeatherLiveData
        coEvery {
            currentWeatherRemoteDataSource.getCurrentWeatherByGeoCords(
                lat,
                lon,
                Constants.Coords.METRIC
            )
        } returns
                Response.success(createSampleCurrentWeatherResponse())
        val mockedObserver: Observer<Resource<CurrentWeatherEntity>> = mockk(relaxUnitFun = true)
        currentWeatherRepository
            .loadCurrentWeatherByGeoCords(lat, lon, fetchRequired, Constants.Coords.METRIC)
            .observeForever(mockedObserver)
        /**
         * shouldFetch == false -> loadFromDb()
         */

        // Make sure network wasn't called
        coVerify {
            currentWeatherRemoteDataSource.getCurrentWeatherByGeoCords(
                lat,
                lon,
                Constants.Coords.METRIC
            ) wasNot called
        }
        // Make sure db called
        verify { currentWeatherLocalDataSource.getCurrentWeather() }

        // Then
        val slot = mutableListOf<Resource<CurrentWeatherEntity>>()
        verify { mockedObserver.onChanged(capture(slot)) }

        val currentWeatherEntity = slot[0]
        Assert.assertEquals(Status.SUCCESS, currentWeatherEntity.status)
        Assert.assertEquals("Istanbul", currentWeatherEntity.data?.name)
        Assert.assertEquals(1, currentWeatherEntity.data?.id)
    }

    @Test
    fun `given fetchRequired = true, when getCurrentWeather called, then make sure network called`() {

        // Given
        val fetchRequired = true
        val lat = 30.0
        val lon = 34.0
        val currentWeatherLiveData: MutableLiveData<CurrentWeatherEntity> = MutableLiveData()
        currentWeatherLiveData.postValue(CurrentWeatherEntity(createSampleCurrentWeatherResponse()))

        val mockedObserver: Observer<Resource<CurrentWeatherEntity>> = mockk(relaxUnitFun = true)

        // When
        coEvery {
            currentWeatherRemoteDataSource.getCurrentWeatherByGeoCords(
                lat,
                lon,
                Constants.Coords.METRIC
            )
        } returns      Response.success(createSampleCurrentWeatherResponse())
        coEvery {
            currentWeatherLocalDataSource.insertCurrentWeather(
                createSampleCurrentWeatherResponse()
            )
        } just runs
        coEvery { currentWeatherLocalDataSource.getCurrentWeather() } returns currentWeatherLiveData

        currentWeatherRepository
            .loadCurrentWeatherByGeoCords(lat, lon, fetchRequired, Constants.Coords.METRIC)
            .observeForever(mockedObserver)

        /**
         * shouldFetch == true -> createCall() -> saveCallResult() -> loadFromDb()
         */

        // Make sure network called
        coEvery {
            currentWeatherRemoteDataSource.getCurrentWeatherByGeoCords(
                lat,
                lon,
                Constants.Coords.METRIC
            )
        }
        // Make sure db called
        verify { currentWeatherLocalDataSource.getCurrentWeather() }

        // Then
        val currentWeatherEntitySlots = mutableListOf<Resource<CurrentWeatherEntity>>()
        verify { mockedObserver.onChanged(capture(currentWeatherEntitySlots)) }
        Assert.assertEquals(1, currentWeatherEntitySlots.size)
        val currentWeatherEntity = currentWeatherEntitySlots[0]
        Assert.assertEquals(Status.SUCCESS, currentWeatherEntity.status)
        Assert.assertEquals("Istanbul", currentWeatherEntity.data!!.name)
        // CurrentWeatherEntity(currentWeatherResponse : CurrentWeatherResponse) constructor defines id as 0

        Assert.assertEquals(0, currentWeatherEntity.data?.id)

    }
}
