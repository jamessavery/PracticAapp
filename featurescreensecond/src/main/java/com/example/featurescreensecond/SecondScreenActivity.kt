package com.example.featurescreensecond


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import com.example.featurescreensecond.databinding.ActivitySecondScreenBinding
import kotlinx.coroutines.launch
import javax.inject.Inject


class SecondScreenActivity : AppCompatActivity(), LifecycleOwner {

    @Inject
    lateinit var secondScreenViewModel: SecondScreenViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySecondScreenBinding
    private lateinit var viewModel: SecondScreenViewModel
    //private val viewModel: ViewModelProvider(this, DemoViewModelFactory(repositoryObject)).get(SecondScreenViewModel::class.java)
//    private val viewModel: SecondScreenViewModel by viewModels {
//        SecondScreenViewModel.provideFactory(stateSingletonImpl, this)
//}

    //private val openingViewModel: OpeningViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as PracticeApplication).appComponent.inject(this)
//        SecondScreenComponent.factory().create(appComponent).inject(this)
        //(applicationContext as PracticeApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO deprecated
        val vm = ViewModelProviders.of(this, viewModelFactory)[SecondScreenViewModel::class.java]

        setupListeners()
        setupObservers()
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
            // COLLECT ABOVE FROM SINGLETON
            lifecycleScope.launchWhenCreated {
                viewModel.loadTfl()
            }
        }
    }

}