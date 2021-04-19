package com.doapps.android.weatherapp.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseActivity<ViewModel : BaseViewModel, DB : ViewDataBinding>(@LayoutRes val layout: Int) :
    AppCompatActivity() {
    abstract val viewModel: ViewModel

    open lateinit var binding: DB

    // lateinit var dataBindingComponent: DataBindingComponent
    private fun initBinding() {

        try{
            binding = DataBindingUtil.setContentView(this , layout)

        }catch (e:Exception){
            binding = DataBindingUtil.inflate(layoutInflater, layout, null, false)
            setContentView(binding.root)
        }

        binding.lifecycleOwner = this

    }

    open fun onCreateLogic(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        onCreateLogic()
    }


}
