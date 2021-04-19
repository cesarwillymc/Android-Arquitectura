package com.doapps.android.weatherapp.dao

import android.os.Build
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.doapps.android.weatherapp.db.WeatherDatabase
import com.doapps.android.weatherapp.db.dao.CitiesForSearchDao
import com.doapps.android.weatherapp.db.entity.CitiesForSearchEntity
import com.doapps.android.weatherapp.util.generateCitiesForSearchEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.koin.test.KoinTest
import org.koin.test.inject
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.robolectric.annotation.Config


@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class CitiesForSearchDaoTest : KoinTest {


    private val weatherDatabase: WeatherDatabase by inject()
    private val citiesForSearchDao: CitiesForSearchDao by inject()

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
    fun `insert one entity and db count must be 1`() {
        // When
        citiesForSearchDao.insertCity(generateCitiesForSearchEntity("aushı1h3", "Istanbul"))
        // Then
        val count = citiesForSearchDao.getCount()
        assertEquals(1,count)
    }

    @Test
    fun `insert two entities and get one of them with city name`() {
        val observer = mockk<Observer<List<CitiesForSearchEntity>>>()
        val slot = slot<List<CitiesForSearchEntity>>()
        // When
        citiesForSearchDao.insertCity(generateCitiesForSearchEntity("kaldsadj", "Ankara"))
        citiesForSearchDao.insertCity(generateCitiesForSearchEntity("opıjıwqk", "Istanbul"))

        // Then
        citiesForSearchDao.getCityByName("Istanbul").observeForever(observer)
        every { observer.onChanged(capture(slot)) } answers {
            assertEquals("opıjıwqk", slot.captured.first().id)
        }


    }

    @Test
    fun `insert two entities and get one of them`()  {
        val observer = mockk<Observer<List<CitiesForSearchEntity>>>()
        val slot = slot<List<CitiesForSearchEntity>>()
        // When
        citiesForSearchDao.insertCity(generateCitiesForSearchEntity("kaldsadj", "Ankara"))
        citiesForSearchDao.insertCity(generateCitiesForSearchEntity("opıjıwqk", "Istanbul"))

        // Then
       citiesForSearchDao.getCities().observeForever(observer)
        every { observer.onChanged(capture(slot)) } answers {
            assertEquals("kaldsadj", slot.captured.first().id)
        }
    }

    @Test
    fun `insert multiple entites and then delete all finally count must be zero`() {
        // When
        citiesForSearchDao.insertCity(generateCitiesForSearchEntity("kaldsadj", "Ankara"))
        citiesForSearchDao.insertCity(generateCitiesForSearchEntity("opıjıwqk", "Istanbul"))
        citiesForSearchDao.insertCity(generateCitiesForSearchEntity("asdadoasd", "Adana"))

        val count = citiesForSearchDao.getCount()
        assertEquals(3,count)

        // Then
        citiesForSearchDao.deleteCities()
        val newCount = citiesForSearchDao.getCount()
        assertEquals(0,newCount)
    }

    @Test
    fun `when is empty count must be 0`() {
        // When
        val count = citiesForSearchDao.getCount()

        // Then
        assertEquals(0,count)
    }
}
