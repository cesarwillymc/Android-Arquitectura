package com.doapps.android.weatherapp.ui.search

import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.doapps.android.weatherapp.R
import com.doapps.android.weatherapp.core.BaseFragment
import com.doapps.android.weatherapp.databinding.FragmentSearchBinding
import com.doapps.android.conexionmodule.db.room.entity.CitiesForSearchEntity
import com.doapps.android.conexionmodule.utils.tryCatch
import com.doapps.android.domain.usecase.SearchCitiesUseCase
import com.doapps.android.weatherapp.ui.search.result.SearchResultAdapter
import com.doapps.android.weatherapp.utils.extensions.hideKeyboard
import com.doapps.android.weatherapp.utils.extensions.observeWith
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>(R.layout.fragment_search) {
    override val viewModel: SearchViewModel by viewModel()
    override fun onCreateConfig() {
        super.onCreateConfig()
        initSearchResultsAdapter()
        initSearchView()

        binding.viewModel?.getSearchViewState()?.observeWith(
            viewLifecycleOwner
        ) {
            binding.viewState = it
            it.data?.let { results -> initSearchResultsRecyclerView(results) }
        }
    }

    private fun initSearchView() {
        val searchEditText: EditText = binding.searchView.findViewById(R.id.search_src_text)
        activity?.applicationContext?.let { ContextCompat.getColor(it, R.color.mainTextColor) }
            ?.let { searchEditText.setTextColor(it) }
        activity?.applicationContext?.let { ContextCompat.getColor(it, android.R.color.darker_gray) }
            ?.let { searchEditText.setHintTextColor(it) }
        binding.searchView.isActivated = true
        binding.searchView.setIconifiedByDefault(false)
        binding.searchView.isIconified = false

        val searchViewSearchIcon = binding.searchView.findViewById<ImageView>(R.id.search_mag_icon)
        searchViewSearchIcon.setImageResource(R.drawable.ic_search)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String): Boolean {
                if (newText.isNotEmpty() && newText.count() > 2) {
                    binding.viewModel?.setSearchParams(SearchCitiesUseCase.SearchCitiesParams(newText))
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isNotEmpty() == true && newText.count() > 2) {
                    binding.viewModel?.setSearchParams(SearchCitiesUseCase.SearchCitiesParams(newText))
                }
                return true
            }
        })
    }

    private fun initSearchResultsAdapter() {
        val adapter = SearchResultAdapter { item ->
            item.coord?.let { it ->
                binding.viewModel?.saveCoordsToSharedPref(it)!!.observe(this,{result->
                    if(result){
                        tryCatch(
                            tryBlock = {
                                binding.searchView.hideKeyboard((requireActivity()))
                            }
                        )

                        findNavController().navigate(R.id.action_searchFragment_to_dashboardFragment)
                    }
                })

            }
        }

        binding.recyclerViewSearchResults.adapter = adapter
        binding.recyclerViewSearchResults.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun initSearchResultsRecyclerView(list: List<CitiesForSearchEntity>) {
        (binding.recyclerViewSearchResults.adapter as SearchResultAdapter).submitList(list.distinctBy { it.getFullName() }.sortedBy { it.importance })
    }
}
