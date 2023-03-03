package com.example.featurescreensecond


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.lifecycle.*
import androidx.navigation.ui.AppBarConfiguration
import com.example.data.StateSingletonImpl
import com.example.featurescreensecond.databinding.ActivitySecondScreenBinding
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.launch
import javax.inject.Inject


class SecondScreenActivity : DaggerAppCompatActivity(), LifecycleOwner {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySecondScreenBinding
    private val viewModel: SecondScreenViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        with(binding) {
            secondScreenTV.text = viewModel.getTriggeredTing() ?: "Second Screen"
        }
    }

    private fun setupObservers() {}

}