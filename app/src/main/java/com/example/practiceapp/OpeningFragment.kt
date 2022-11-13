package com.example.practiceapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory

// Todo LiveData next
class OpeningFragment : Fragment(R.layout.activity_opening) {

    private val viewModel: OpeningViewModel by viewModels()
    //Todo how to get key to over here

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    companion object {

    }
}