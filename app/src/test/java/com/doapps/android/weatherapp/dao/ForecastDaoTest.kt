package com.doapps.android.weatherapp.dao

import android.os.Build
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.doapps.android.weatherapp.db.WeatherDatabase
import com.doapps.android.weatherapp.db.dao.ForecastDao
import com.doapps.android.weatherapp.db.entity.ForecastEntity
import com.doapps.android.weatherapp.util.createSampleForecastResponse
import com.doapps.android.weatherapp.util.createSampleForecastWithCoord
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.annotation.Config


@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class ForecastDaoTest:KoinTest {


    private val weatherDatabase: WeatherDatabase by inject()
    private val forecastDao: ForecastDao by inject()
    val dbTestModule = module {
        single<WeatherDatabase> {
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                WeatherDatabase::class.java
            )
                .allowMainThreadQueries()
                .build()
        }
        factory { get<WeatherDatabase>().forecastDao() }
        factory { get<WeatherDatabase>().currentWeatherDao() }
        factory { get<WeatherDatabase>().citiesForSearchDao() }
    }

    @Before
    fun before() {
        stopKoin() // to remove 'A Koin Application has already been started'
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(dbTestModule)
        }
    }

    @After
    fun after() {

        weatherDatabase.close()
        stopKoin()
    }

    @Test
    fun `insert a forecast to forecast dao`() {
        val observer = mockk<Observer<ForecastEntity>>()
        val slot = slot<ForecastEntity>()
        // When
        forecastDao.insertForecast(createSampleForecastResponse(3, "Istanbul"))

        // Then
        forecastDao.getForecast().observeForever(observer)

        every { observer.onChanged(capture(slot)) } answers {
            assertEquals("Turkey", slot.captured.city?.cityCountry)
            assertEquals("Istanbul", slot.captured.city?.cityName)
        }
    }

    @Test
    fun `insert two forecast to forecast dao and then delete all after this operations count must be 0`() {
        // When
        forecastDao.insertForecast(createSampleForecastResponse(3, "Istanbul"))
        forecastDao.insertForecast(createSampleForecastResponse(4, "Ankara"))

        val value = forecastDao.getCount()
        assertEquals(2, value)

        // Then
        forecastDao.deleteAll()
        val newValue = forecastDao.getCount()
        assertEquals(0, newValue)
    }

    @Test
    fun `insert a forecast and then update`() {
        val observer = mockk<Observer<ForecastEntity>>()
        val slot = slot<ForecastEntity>()
        // When
        forecastDao.insertForecast(createSampleForecastResponse(1, "Istanbul"))
        forecastDao.getForecast().observeForever(observer)
        every { observer.onChanged(capture(slot)) } answers {
            assertEquals("Istanbul", slot.captured.city?.cityName)
        }

        // Then
        forecastDao.updateForecast(createSampleForecastResponse(1, "Ankara"))

        every { observer.onChanged(capture(slot)) } answers {
            assertEquals("Ankara", slot.captured.city?.cityName)
        }
    }

    @Test
    fun `delete and insert a forecast`() {
        val observer = mockk<Observer<ForecastEntity>>()
        val slot = slot<ForecastEntity>()
        // When
        forecastDao.insertForecast(createSampleForecastResponse(1, "Istanbul"))
        val count = forecastDao.getCount()
        assertEquals(1, count)

        // Then
        forecastDao.deleteAndInsert(createSampleForecastResponse(2, "Adana"))
        val newCount = forecastDao.getCount()
        forecastDao.getForecast().observeForever(observer)
        every { observer.onChanged(capture(slot)) } answers {
            assertEquals("Adana", slot.captured.city?.cityName)
        }
        assertEquals(1, newCount)
    }

    @Test
    fun `first insert a forecast then delete and count must be zero`() {
        // When
        forecastDao.insertForecast(createSampleForecastResponse(1, "Kayseri"))
        val count = forecastDao.getCount()
        assertEquals(1, count)

        // Then
        forecastDao.deleteForecast(createSampleForecastResponse(1, "Kayseri"))
        val newCount = forecastDao.getCount()
        assertEquals(0, newCount)
    }

    @Test
    fun `first insert a forecast and then get it with coords`() {
        val observer = mockk<Observer<ForecastEntity>>()
        val slot = slot<ForecastEntity>()
        // When
        forecastDao.insertForecast(createSampleForecastWithCoord(1, "Adana", 1.0, 2.0))
        forecastDao.insertForecast(createSampleForecastWithCoord(3, "Kayseri", 10.0, 30.0))
        val count = forecastDao.getCount()
        assertEquals(2, count)

        // Then
        forecastDao.getForecastByCoord(10.0, 30.0).observeForever(observer)
        every { observer.onChanged(capture(slot)) } answers {
            assertEquals("Kayseri", slot.captured.city?.cityName)
        }
    }
}
