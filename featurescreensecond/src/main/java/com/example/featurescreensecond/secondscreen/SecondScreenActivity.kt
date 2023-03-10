package com.example.featurescreensecond.secondscreen


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.lifecycle.*
import androidx.navigation.ui.AppBarConfiguration
import com.example.featurescreensecond.databinding.ActivitySecondScreenBinding
import dagger.android.support.DaggerAppCompatActivity
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
        // Coroutine here auto-starts only upon reaching onStart() lifecycle state
        lifecycleScope.launchWhenStarted {
            viewModel.ting.collect {
                Log.e("JIMMY219", "COllected from Act - $it")
                // Update views with the data.
            }
        }
    }

    private fun setupListeners() {
        with(binding) {
            secondScreenTV.text = viewModel.getTriggeredTing() ?: "Second Screen"
        }
    }

    private fun setupObservers() {
        viewModel.viewData.observe(this@SecondScreenActivity, Observer {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        })
    }

}