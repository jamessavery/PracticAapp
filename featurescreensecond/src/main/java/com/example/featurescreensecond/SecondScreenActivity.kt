package com.example.featurescreensecond

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import com.example.featurescreensecond.databinding.ActivitySecondScreenBinding
import kotlinx.coroutines.launch
import javax.inject.Inject


class SecondScreenActivity : AppCompatActivity(), LifecycleOwner {

    @Inject
    lateinit var openingViewModel: SecondScreenViewModel

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySecondScreenBinding
    private val viewModel: SecondScreenViewModel by viewModels()
    //private val openingViewModel: OpeningViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        //(applicationContext as PracticeApplication).appComponent.inject(this)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupListeners()
        setupObservers()
        super.onCreate(savedInstanceState)
    }

    private fun setupListeners() {
//        with(binding) {
//
//        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
//            openingViewModel.secondScreenState.flowWithLifecycle(lifecycle)
//                .collect { secondScreenState: OpeningViewModel.SecondScreenState ->
//                    if (secondScreenState == OpeningViewModel.SecondScreenState.Triggered) {
//                        binding.secondScreenFragmentContainer.text = "TRIGGERED"
//                    }
//                }
            lifecycleScope.launchWhenCreated {
//                viewModel.loadTfl()
            }
        }
    }

}