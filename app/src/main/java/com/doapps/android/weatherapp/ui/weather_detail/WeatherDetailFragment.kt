package com.doapps.android.weatherapp.ui.weather_detail

import android.transition.TransitionInflater
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.doapps.android.weatherapp.R
import com.doapps.android.weatherapp.core.BaseFragment
import com.doapps.android.weatherapp.databinding.FragmentWeatherDetailBinding
import com.doapps.android.weatherapp.domain.model.ListItem
import com.doapps.android.weatherapp.ui.weather_detail.weatherHourOfDay.WeatherHourOfDayAdapter
import com.doapps.android.weatherapp.utils.extensions.observeWith
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherDetailFragment : BaseFragment<WeatherDetailViewModel, FragmentWeatherDetailBinding>(
    R.layout.fragment_weather_detail,
) {

    override val viewModel: WeatherDetailViewModel by viewModel()

    private val weatherDetailFragmentArgs: WeatherDetailFragmentArgs by navArgs()


    override fun onCreateConfig() {
        super.onCreateConfig()
        binding.viewModel?.weatherItem?.set(weatherDetailFragmentArgs.weatherItem)
        binding.viewModel?.selectedDayDate = weatherDetailFragmentArgs.weatherItem.dtTxt?.substringBefore(" ")

        binding.viewModel?.getForecast()?.observeWith(viewLifecycleOwner) {
            binding.viewModel?.selectedDayForecastLiveData
                ?.postValue(
                    it.list?.filter { item ->
                        item.dtTxt?.substringBefore(" ") == binding.viewModel?.selectedDayDate
                    }
                )
        }

        binding.viewModel?.selectedDayForecastLiveData?.observeWith(
            viewLifecycleOwner
        ) {
            initWeatherHourOfDayAdapter(it)
        }

        binding.fabClose.setOnClickListener {
            findNavController().popBackStack()
        }

        val inflateTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = inflateTransition
    }

    private fun initWeatherHourOfDayAdapter(list: List<ListItem>) {
        val adapter = WeatherHourOfDayAdapter { item ->
        }

        binding.recyclerViewHourOfDay.adapter = adapter
        (binding.recyclerViewHourOfDay.adapter as WeatherHourOfDayAdapter).submitList(list)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
