package com.example.practiceapp.opening

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import com.example.practiceapp.R
import com.example.practiceapp.databinding.ActivityOpeningBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.launch

class OpeningActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityOpeningBinding
    private val viewModel: OpeningViewModel by viewModels()

    private var isExpanded: Boolean = false

    private val bottomSheetView by lazy { findViewById<ConstraintLayout>(R.id.bottomSheet) }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityOpeningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        setListeners()
        setUpObservers()
        super.onCreate(savedInstanceState)
    }

    private fun setListeners() {
        with(binding) {
            bottomSheetButton.setOnClickListener { viewModel.onBottomSheetButtonClicked(isExpanded) }
            liveDataButton.setOnClickListener {
                var countIndex = viewModel.getUpdatedCount()
                Log.e("jimmy", "jimmy count - $countIndex")
                lifecycleScope.launch() {
                    viewModel.loadTfl()
                    // Pass value of this if successful into
                }
            }
        }
    }

    // https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda#:~:text=Use%20the%20Lifecycle.,the%20UI%20layer%20in%20Android.
    private fun setUpObservers() {
        lifecycleScope.launch {
            viewModel.event.flowWithLifecycle(lifecycle).collect() { event ->
                when (event) {
                    OpeningViewModel.Event.OpenBottomSheet -> openBottomSheet()
                    OpeningViewModel.Event.CloseBottomSheet -> closeBottomSheet()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.loadingState.flowWithLifecycle(lifecycle).collect() { loadingState ->
                when (loadingState) {
                    OpeningViewModel.LoadingState.Idle -> binding.progressCircular.visibility =
                        View.GONE
                    OpeningViewModel.LoadingState.Loading -> binding.progressCircular.visibility =
                        View.VISIBLE
                }
            }
        }
        lifecycleScope.launch {
            viewModel.count.observe(this@OpeningActivity, Observer {
                Log.e("jimmy", "jimmy1231233 - $it")
            })
        }
    }

    override fun onBackPressed() {
        if (isExpanded) {
            Log.e("jimmy", isExpanded.toString())
            closeBottomSheet()
        } else {
            super.onBackPressed()
        }
    }

    private fun openBottomSheet() {
        isExpanded = !isExpanded // inverts the boolean
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun closeBottomSheet() {
        isExpanded = !isExpanded // inverts the boolean
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}