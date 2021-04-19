package com.doapps.android.weatherapp.ui.splash

import android.content.SharedPreferences
import com.doapps.android.weatherapp.core.BaseViewModel




class SplashFragmentViewModel (var sharedPreferences: SharedPreferences) : BaseViewModel() {
    var navigateDashboard = false
}
