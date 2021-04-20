package com.doapps.android.weatherapp.repo

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.doapps.android.weatherapp.core.Constants
import com.doapps.android.conexionmodule.db.room.entity.ForecastEntity
import com.doapps.android.domain.datasource.forecast.ForecastLocalDataSource
import com.doapps.android.domain.datasource.forecast.ForecastRemoteDataSource
import com.doapps.android.domain.repo.forecast.ForecastRepositoryImpl
import com.doapps.android.weatherapp.util.createSampleForecastResponse
import com.doapps.android.domain.utils.status.Resource
import com.doapps.android.domain.utils.status.Status
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config
import retrofit2.Response



@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class ForecastRepositoryTest {


    @MockK
    lateinit var forecastRemoteDataSource: ForecastRemoteDataSource

    @MockK
    lateinit var forecastLocalDataSource: ForecastLocalDataSource

    private lateinit var forecastRepository: ForecastRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        forecastRepository = ForecastRepositoryImpl(forecastRemoteDataSource, forecastLocalDataSource)
    }
    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `given fetchRequired = false, when loadForecastByCoord called, then make sure db called`() {
        // Given
        val fetchRequired = false
        val lat = 30.0
        val lon = 34.0
        val forecastLiveData: MutableLiveData<ForecastEntity> = MutableLiveData()
        forecastLiveData.postValue(createSampleForecastResponse(1, "Istanbul"))
        val mockedObserver: Observer<Resource<ForecastEntity>> = mockk(relaxUnitFun = true)

        // When
        every { forecastLocalDataSource.getForecast() } returns forecastLiveData
        coEvery { forecastRemoteDataSource.getForecastByGeoCords(lat, lon, Constants.Coords.METRIC) } returns
            Response.success(createSampleForecastResponse())

        forecastRepository
            .loadForecastByCoord(lat, lon, fetchRequired, Constants.Coords.METRIC)
            .observeForever(mockedObserver)

        /**
         * shouldFetch == false -> loadFromDb()
         */

        // Make sure network wasn't called
        coVerify { forecastRemoteDataSource.getForecastByGeoCords(lat, lon, Constants.Coords.METRIC) wasNot called }
        // Make sure db called
        verify { forecastLocalDataSource.getForecast() }

        // Then
        val forecastEntitySlots = mutableListOf<Resource<ForecastEntity>>()
        verify { mockedObserver.onChanged(capture(forecastEntitySlots)) }

        val forecastEntity = forecastEntitySlots[0]
        assertEquals(Status.SUCCESS, forecastEntity.status)
        assertEquals("Istanbul", forecastEntity.data?.city?.cityName)
        assertEquals(1, forecastEntity.data?.id)
    }

    @Test
    fun `given fetchRequired = true, when loadForecastByCoord called, then make sure network called`() {
        // Given
        val fetchRequired = true
        val lat = 30.0
        val lon = 34.0
        val forecastLiveData: MutableLiveData<ForecastEntity> = MutableLiveData()
        forecastLiveData.postValue(ForecastEntity(createSampleForecastResponse()))
        val mockedObserver: Observer<Resource<ForecastEntity>> = mockk(relaxUnitFun = true)

        // When
        coEvery { forecastRemoteDataSource.getForecastByGeoCords(lat, lon, Constants.Coords.METRIC) } returns Response.success(createSampleForecastResponse())
        every { forecastLocalDataSource.insertForecast(createSampleForecastResponse()) } just runs
        every { forecastLocalDataSource.getForecast() } returns forecastLiveData

        forecastRepository
            .loadForecastByCoord(lat, lon, fetchRequired, Constants.Coords.METRIC)
            .observeForever(mockedObserver)

        /**
         * shouldFetch == true -> createCall() -> saveCallResult() -> loadFromDb()
         */

        // Make sure network called
        coVerify { forecastRemoteDataSource.getForecastByGeoCords(lat, lon, Constants.Coords.METRIC) }
        // Make sure db called
        verify { forecastLocalDataSource.getForecast() }

        // Then
        val forecastEntitySlots = mutableListOf<Resource<ForecastEntity>>()
        verify { mockedObserver.onChanged(capture(forecastEntitySlots)) }

        val forecastEntity = forecastEntitySlots[0]

        assertEquals(0, forecastEntity.data?.id)
        assertEquals("Istanbul",forecastEntity.data?.city?.cityName)
    }
}
