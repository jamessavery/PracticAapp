package com.example.practiceapp.secondScreen

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
import com.example.practiceapp.databinding.ActivitySecondScreenBinding
import com.example.practiceapp.databinding.ActivitySecondScreenBinding.inflate
import com.example.practiceapp.databinding.FragmentSecondScreenBinding.inflate
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.launch


class SecondScreenActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySecondScreenBinding
    //private val viewModel: OpeningViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        super.onCreate(savedInstanceState)
    }

}