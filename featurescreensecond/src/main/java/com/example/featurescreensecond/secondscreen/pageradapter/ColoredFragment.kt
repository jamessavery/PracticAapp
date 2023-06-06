package com.example.featurescreensecond.secondscreen.pageradapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.featurescreensecond.databinding.FragmentMainBinding
import com.example.featurescreensecond.secondscreen.SecondScreenViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ColoredFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var binding: FragmentMainBinding? = null

    private val viewModel: SecondScreenViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentMainBinding.inflate(inflater, container, false)
        arguments?.getInt(ARG_BG_COLOR)?.let {
            binding?.root?.setBackgroundColor(it)
        }

        binding?.sectionLabel?.setOnClickListener {
            Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
            viewModel.testShareIn2()
        }

        return binding?.root
    }

    companion object {
        private const val ARG_BG_COLOR = "background_color"

        @JvmStatic
        fun newInstance(color: Int): ColoredFragment {
            return ColoredFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_BG_COLOR, color)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.binding = null
    }
}