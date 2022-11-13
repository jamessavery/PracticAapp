package com.example.practiceapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import com.example.practiceapp.databinding.ActivityOpeningBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.launch

class OpeningActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityOpeningBinding
    private val viewModel: OpeningViewModel by viewModels()

    private var isExpanded: Boolean = true // TODO extract to extension function / some manager

    private val bottomSheetView by lazy { findViewById<ConstraintLayout>(R.id.bottomSheet) }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityOpeningBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val navController = findNavController(R.id.nav_host_fragment_content_opening)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

        setListeners()
        setUpObservers()

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        toggleBottomSheet() // Hidden by default it seems
    }

    private fun toggleBottomSheet() {
        if (isExpanded) {
           isExpanded = false
        } else {
            isExpanded = true
        }

        val updatedState = if (!isExpanded) BottomSheetBehavior.STATE_COLLAPSED else BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.state = updatedState
    }

    private fun setListeners() {
        with (binding) {
            bottomSheetButton.setOnClickListener { viewModel.onBottomSheetButtonClicked() }
        }
    }

    // https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda#:~:text=Use%20the%20Lifecycle.,the%20UI%20layer%20in%20Android.
    private fun setUpObservers() {
        lifecycleScope.launch {
            viewModel.event.flowWithLifecycle(lifecycle).collect() {
                renderEvent()
            }
        }
    }

    private fun renderEvent() {
        toggleBottomSheet()
        Toast.makeText(applicationContext, "JIMMY", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if(isExpanded) {
            Log.e("jimmy", isExpanded.toString())
            toggleBottomSheet()
        } else {
            super.onBackPressed()
        }
    }


//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_opening)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}