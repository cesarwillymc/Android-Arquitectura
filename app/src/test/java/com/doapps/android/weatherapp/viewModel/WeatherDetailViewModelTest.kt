package com.doapps.android.weatherapp.viewModel

import android.os.Build
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.doapps.android.conexionmodule.db.room.WeatherDatabase
import com.doapps.android.conexionmodule.db.room.entity.ForecastEntity
import com.doapps.android.weatherapp.di.module.dataSourceModule
import com.doapps.android.weatherapp.di.module.repositoryModule
import com.doapps.android.weatherapp.di.module.viewModelModule
import com.doapps.android.weatherapp.domain.datasource.forecast.ForecastLocalDataSource
import com.doapps.android.weatherapp.ui.weather_detail.WeatherDetailViewModel
import com.doapps.android.weatherapp.util.createSampleForecastResponse
import io.mockk.*
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
class WeatherDetailViewModelTest: KoinTest{



    private val weatherDetailViewModel: WeatherDetailViewModel by inject()
    private val weatherDatabase: WeatherDatabase by inject()
    private val forecastLocalDataSource: ForecastLocalDataSource by inject()
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
            modules(dbTestModule, dataSourceModule, viewModelModule, repositoryModule)
        }
    }
    @After
    fun closeDatabase() {
        weatherDatabase.close()
        stopKoin()
    }

    @Test
    fun `insert forecast and when getForecast called the livedata result must be ForecastEntity`() {
        val observer = mockk<Observer<ForecastEntity>>()
        val slot = slot<ForecastEntity>()
        // When
        forecastLocalDataSource.insertForecast(createSampleForecastResponse())

        // Then
        weatherDetailViewModel.getForecast().observeForever(observer)
        every { observer.onChanged(capture(slot)) } answers {
            assertEquals("Istanbul", slot.captured.city?.cityName)
        }

    }
}
