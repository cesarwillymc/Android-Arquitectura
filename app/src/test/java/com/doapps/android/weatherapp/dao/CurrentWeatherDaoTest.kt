package com.doapps.android.weatherapp.dao

import android.os.Build
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.doapps.android.weatherapp.db.WeatherDatabase
import com.doapps.android.weatherapp.db.dao.CurrentWeatherDao
import com.doapps.android.weatherapp.db.entity.CurrentWeatherEntity
import com.doapps.android.weatherapp.util.generateCurrentWeatherEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
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
class CurrentWeatherDaoTest : KoinTest {

    private val weatherDatabase: WeatherDatabase by inject()
    private val currentWeatherDao: CurrentWeatherDao by inject()
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
    fun `empty database count must be zero`() {
        // When
        val count = currentWeatherDao.getCount()

        // Then
        assertEquals(0, count)
    }

    @Test
    fun `insert one entity and count must be one`() = runBlocking {
        // When
        currentWeatherDao.insertCurrentWeather(generateCurrentWeatherEntity("Istanbul", 1))
        val count = currentWeatherDao.getCount()
        assertEquals(1, count)
    }

    @Test
    fun `insert one entity and test get function`() {
        val observer = mockk<Observer<CurrentWeatherEntity>>()
        val slot = slot<CurrentWeatherEntity>()
        // When
        runBlocking {
            currentWeatherDao.insertCurrentWeather(generateCurrentWeatherEntity("Istanbul", 1))
        }


        // Then
        currentWeatherDao.getCurrentWeather().observeForever(observer)
        every { observer.onChanged(capture(slot)) } answers {
            assertEquals("Istanbul", slot.captured.name)
        }
    }

    @Test
    fun `delete and insert a current weather`() {
        val observer = mockk<Observer<CurrentWeatherEntity>>()
        val slot = slot<CurrentWeatherEntity>()
        // When
        runBlocking {
            currentWeatherDao.deleteAndInsert(generateCurrentWeatherEntity("Istanbul", 1))
        }
        val count = currentWeatherDao.getCount()
        assertEquals(1, count)
        // Then
        runBlocking { currentWeatherDao.deleteAndInsert(generateCurrentWeatherEntity("Adana", 2)) }
        val newCount = currentWeatherDao.getCount()
        currentWeatherDao.getCurrentWeather().observeForever(observer)
        every { observer.onChanged(capture(slot)) } answers {
            assertEquals("Adana", slot.captured.name)
        }
        assertEquals(1, newCount)

    }

    @Test
    fun `first insert a current weather then delete and count must be zero`() {
        // When
        runBlocking { currentWeatherDao.deleteAndInsert(generateCurrentWeatherEntity("Istanbul", 1)) }
        val count = currentWeatherDao.getCount()
        assertEquals(1, count)

        // Then
        runBlocking {  currentWeatherDao.deleteCurrentWeather()  }
        val newCount = currentWeatherDao.getCount()
        assertEquals(0, newCount)
    }
}
