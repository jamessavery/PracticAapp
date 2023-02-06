package com.example.practiceapp.opening

import android.content.Intent
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.practiceapp.R
import com.example.practiceapp.databinding.ActivityOpeningBinding
import com.example.practiceapp.secondScreen.SecondScreenActivity
import com.example.practiceapp.secondScreen.SecondScreenFragment
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

    // @see https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda#:~:text=Use%20the%20Lifecycle.,the%20UI%20layer%20in%20Android.
    private fun setListeners() {
        with(binding) {
            bottomSheetButton.setOnClickListener { viewModel.onBottomSheetButtonClicked(isExpanded) }
            liveDataButton.setOnClickListener {
                val countIndex = viewModel.getUpdatedCount()
                Log.e("jimmy", "jimmy count - $countIndex")
                lifecycleScope.launch() {
                    //val toasted = viewModel.testThis().subscribe()
                    //Toast.makeText(applicationContext, toasted.toString(), Toast.LENGTH_SHORT).show()
                    viewModel.practiceRx()
                }
            }
            secondScreenButton.setOnClickListener {
                startSecondFrag()
            }
        }
    }

    // Todo should be in ViewModel..?
    private fun startSecondFrag() {
        //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

        //supportFragmentManager.beginTransaction().add(com.example.practiceapp.R.id.second_screen_activity, SecondScreenActivity()).commit()

        // So issue seems that starting a frag, where that frag doesnt have an act, IT DOESNT WORK
        // So to get previous code working (improve understanding) I gotta somehow tie my frag to an act..? Use new project NavigationTest to mimic how that does it!
        // 1) Use NavigationTest to find way to attach secondFrag to an act then try supportFragmentManager() again!
        // 2) Figure out how to use findNavController() .. Is this what Chrome navigation tabs were about..?
        startActivity(Intent(this, SecondScreenActivity::class.java))
    }

    // @see https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda#:~:text=Use%20the%20Lifecycle.,the%20UI%20layer%20in%20Android.
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