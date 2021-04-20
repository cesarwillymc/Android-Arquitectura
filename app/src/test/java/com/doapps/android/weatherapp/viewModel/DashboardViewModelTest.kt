package com.doapps.android.weatherapp.viewModel

import android.content.SharedPreferences
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.doapps.android.conexionmodule.db.room.entity.CurrentWeatherEntity
import com.doapps.android.conexionmodule.db.room.entity.ForecastEntity
import com.doapps.android.domain.usecase.CurrentWeatherUseCase
import com.doapps.android.domain.usecase.ForecastUseCase
import com.doapps.android.domain.utils.status.Resource
import com.doapps.android.domain.utils.status.Status
import com.doapps.android.weatherapp.ui.dashboard.DashboardFragmentViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config



@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class DashboardViewModelTest {


    @MockK
    lateinit var currentWeatherUseCase: CurrentWeatherUseCase

    @MockK
    lateinit var forecastUseCase: ForecastUseCase

    @MockK
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var dashboardFragmentViewModel: DashboardFragmentViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dashboardFragmentViewModel = DashboardFragmentViewModel(forecastUseCase, currentWeatherUseCase, sharedPreferences)
    }

    @After
    fun after() {
        stopKoin()
    }
    @Test
    fun `given loading state, when setForecastParams called, then update live data for loading status`() {
        // Given
        val viewStateObserver: Observer<Resource<ForecastEntity>> = mockk(relaxUnitFun = true)
        dashboardFragmentViewModel.getForecastViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<Resource<ForecastEntity>> = MutableLiveData()
        viewStateLiveData.postValue(Resource(Status.LOADING, null, null))

        // When
        every { forecastUseCase.execute(any()) } returns viewStateLiveData
        dashboardFragmentViewModel.setForecastParams(ForecastUseCase.ForecastParams("30", "32", true, "metric"))

        // Then
        val forecastViewStateSlots = mutableListOf<Resource<ForecastEntity>>()
        verify { viewStateObserver.onChanged(capture(forecastViewStateSlots)) }

        val loadingState = forecastViewStateSlots[0]
        assertEquals(Status.LOADING, loadingState.status)
    }

    @Test
    fun `given error state, when setForecastParams called, then update live data for error status`() {
        // Given
        val viewStateObserver: Observer<Resource<ForecastEntity>> = mockk(relaxUnitFun = true)
        dashboardFragmentViewModel.getForecastViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<Resource<ForecastEntity>> = MutableLiveData()
        viewStateLiveData.postValue(Resource(Status.ERROR, null,"Unhandled Exception" ))

        // WhenResource<ForecastEntity>
        every { forecastUseCase.execute(any()) } returns viewStateLiveData
        dashboardFragmentViewModel.setForecastParams(ForecastUseCase.ForecastParams("30", "32", true, "metric"))

        // Then
        val forecastViewStateSlots = mutableListOf<Resource<ForecastEntity>>()
        verify { viewStateObserver.onChanged(capture(forecastViewStateSlots)) }

        val errorState = forecastViewStateSlots[0]
        assertEquals(Status.ERROR, errorState.status)
    }

    @Test
    fun `given success state, when setForecastParams called, then update live data for success status`() {
        // Given
        val viewStateObserver: Observer<Resource<ForecastEntity>> = mockk(relaxUnitFun = true)
        dashboardFragmentViewModel.getForecastViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<Resource<ForecastEntity>> = MutableLiveData()
        viewStateLiveData.postValue(Resource(Status.SUCCESS, null, null))

        // When
        every { forecastUseCase.execute(any()) } returns viewStateLiveData
        dashboardFragmentViewModel.setForecastParams(ForecastUseCase.ForecastParams("30", "32", true, "metric"))

        // Then
        val forecastViewStateSlots = mutableListOf<Resource<ForecastEntity>>()
        verify { viewStateObserver.onChanged(capture(forecastViewStateSlots)) }

        val successState = forecastViewStateSlots[0]
        assertEquals(Status.SUCCESS, successState.status)
    }

    @Test
    fun `given loading state, when setCurrentWeatherParams called, then update live data for loading status`() {
        // Given
        val viewStateObserver: Observer<Resource<CurrentWeatherEntity>> = mockk(relaxUnitFun = true)
        dashboardFragmentViewModel.getCurrentWeatherViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<Resource<CurrentWeatherEntity>> = MutableLiveData()
        viewStateLiveData.postValue(Resource<CurrentWeatherEntity>(Status.LOADING, null, null))

        // When
        every { currentWeatherUseCase.execute(any()) } returns viewStateLiveData
        dashboardFragmentViewModel.setCurrentWeatherParams(CurrentWeatherUseCase.CurrentWeatherParams("30", "32", true, "metric"))

        // Then
        val currentWeatherViewStateSlots = mutableListOf<Resource<CurrentWeatherEntity>>()
        verify { viewStateObserver.onChanged(capture(currentWeatherViewStateSlots)) }

        val loadingState = currentWeatherViewStateSlots[0]

        assertEquals(Status.LOADING, loadingState.status)
    }

    @Test
    fun `given error state, when setCurrentWeatherParams called, then update live data for error status`() {
        // Given
        val viewStateObserver: Observer<Resource<CurrentWeatherEntity>> = mockk(relaxUnitFun = true)
        dashboardFragmentViewModel.getCurrentWeatherViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<Resource<CurrentWeatherEntity>> = MutableLiveData()
        viewStateLiveData.postValue(Resource<CurrentWeatherEntity>(Status.ERROR, null, null))

        // When
        every { currentWeatherUseCase.execute(any()) } returns viewStateLiveData
        dashboardFragmentViewModel.setCurrentWeatherParams(CurrentWeatherUseCase.CurrentWeatherParams("30", "32", true, "metric"))

        // Then
        val currentWeatherViewStateSlots = mutableListOf<Resource<CurrentWeatherEntity>>()
        verify { viewStateObserver.onChanged(capture(currentWeatherViewStateSlots)) }

        val errorState = currentWeatherViewStateSlots[0]
        assertEquals(Status.ERROR, errorState.status)
    }

    @Test
    fun `given success state, when setCurrentWeatherParams called, then update live data for success status`() {
        // Given
        val viewStateObserver: Observer<Resource<CurrentWeatherEntity>> = mockk(relaxUnitFun = true)
        dashboardFragmentViewModel.getCurrentWeatherViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<Resource<CurrentWeatherEntity>> = MutableLiveData()
        viewStateLiveData.postValue(Resource<CurrentWeatherEntity>(Status.SUCCESS, null, null))

        // When
        every { currentWeatherUseCase.execute(any()) } returns viewStateLiveData
        dashboardFragmentViewModel.setCurrentWeatherParams(CurrentWeatherUseCase.CurrentWeatherParams("30", "32", true, "metric"))

        // Then
        val currentWeatherViewStateSlots = mutableListOf<Resource<CurrentWeatherEntity>>()
        verify { viewStateObserver.onChanged(capture(currentWeatherViewStateSlots)) }

        val successState = currentWeatherViewStateSlots[0]
        assertEquals(Status.SUCCESS, successState.status)
    }
}
