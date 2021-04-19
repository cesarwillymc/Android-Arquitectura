package com.doapps.android.weatherapp.viewModel

import android.content.SharedPreferences
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.doapps.android.weatherapp.db.entity.CitiesForSearchEntity
import com.doapps.android.weatherapp.domain.usecase.SearchCitiesUseCase
import com.doapps.android.weatherapp.ui.search.SearchViewModel
import com.doapps.android.weatherapp.utils.domain.Resource
import com.doapps.android.weatherapp.utils.domain.Status
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
class SearchViewModelTest {



    @MockK
    lateinit var searchCitiesUseCase: SearchCitiesUseCase

    @MockK
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        searchViewModel = SearchViewModel(searchCitiesUseCase, sharedPreferences)
    }
    @After
    fun after() {
        stopKoin()
    }
    @Test
    fun `given loading state, when setSearchParams called, then update live data for loading status`() {
        // Given
        val viewStateObserver: Observer<Resource<List<CitiesForSearchEntity>>> = mockk(relaxUnitFun = true)
        searchViewModel.getSearchViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<Resource<List<CitiesForSearchEntity>>> = MutableLiveData()
        viewStateLiveData.postValue(Resource<List<CitiesForSearchEntity>>(Status.LOADING, null, null))

        // When
        every { searchCitiesUseCase.execute(any()) } returns viewStateLiveData
        searchViewModel.setSearchParams(SearchCitiesUseCase.SearchCitiesParams("city"))

        // Then
        val forecastViewStateSlots = mutableListOf<Resource<List<CitiesForSearchEntity>>>()
        verify { viewStateObserver.onChanged(capture(forecastViewStateSlots)) }

        val loadingState = forecastViewStateSlots[0]
        assertEquals(Status.LOADING, loadingState.status)
    }

    @Test
    fun `given error state, when setSearchParams called, then update live data for error status`() {
        // Given
        val viewStateObserver: Observer<Resource<List<CitiesForSearchEntity>>> = mockk(relaxUnitFun = true)
        searchViewModel.getSearchViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<Resource<List<CitiesForSearchEntity>>> = MutableLiveData()
        viewStateLiveData.postValue(Resource<List<CitiesForSearchEntity>>(Status.ERROR, null, null))

        // When
        every { searchCitiesUseCase.execute(any()) } returns viewStateLiveData
        searchViewModel.setSearchParams(SearchCitiesUseCase.SearchCitiesParams("city"))

        // Then
        val forecastViewStateSlots = mutableListOf<Resource<List<CitiesForSearchEntity>>>()
        verify { viewStateObserver.onChanged(capture(forecastViewStateSlots)) }

        val errorState = forecastViewStateSlots[0]
        assertEquals(Status.ERROR, errorState.status)
    }

    @Test
    fun `given success state, when setSearchParams called, then update live data for success status`() {
        // Given
        val viewStateObserver: Observer<Resource<List<CitiesForSearchEntity>>> = mockk(relaxUnitFun = true)
        searchViewModel.getSearchViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<Resource<List<CitiesForSearchEntity>>> = MutableLiveData()
        viewStateLiveData.postValue(Resource<List<CitiesForSearchEntity>>(Status.SUCCESS, null, null))

        // When
        every { searchCitiesUseCase.execute(any()) } returns viewStateLiveData
        searchViewModel.setSearchParams(SearchCitiesUseCase.SearchCitiesParams("city"))

        // Then
        val forecastViewStateSlots = mutableListOf<Resource<List<CitiesForSearchEntity>>>()
        verify { viewStateObserver.onChanged(capture(forecastViewStateSlots)) }

        val successState = forecastViewStateSlots[0]
        assertEquals(Status.SUCCESS, successState.status)
    }
}
