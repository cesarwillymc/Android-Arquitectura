package com.doapps.android.weatherapp

import android.os.Build
import com.doapps.android.weatherapp.dao.CitiesForSearchDaoTest
import com.doapps.android.weatherapp.dao.CurrentWeatherDaoTest
import com.doapps.android.weatherapp.dao.ForecastDaoTest
import com.doapps.android.weatherapp.repo.CurrentWeatherRepositoryTest
import com.doapps.android.weatherapp.repo.ForecastRepositoryTest
import com.doapps.android.weatherapp.viewModel.DashboardViewModelTest
import com.doapps.android.weatherapp.viewModel.SearchViewModelTest
import com.doapps.android.weatherapp.viewModel.WeatherDetailViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.robolectric.annotation.Config



@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(Suite::class)
@Suite.SuiteClasses(
    CitiesForSearchDaoTest::class,
    CurrentWeatherDaoTest::class,
    DashboardViewModelTest::class,
    ForecastDaoTest::class,
    SearchViewModelTest::class,
    WeatherDetailViewModelTest::class,
    ForecastRepositoryTest::class,
    CurrentWeatherRepositoryTest::class
)
class TestSuite
