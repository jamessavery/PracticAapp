package com.example.featurescreensecond.secondscreen


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.data.services.response.QuoteList
import com.example.featurescreensecond.R
import com.example.featurescreensecond.databinding.ActivitySecondScreenBinding
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.launch
import javax.inject.Inject


class SecondScreenActivity : DaggerAppCompatActivity(), LifecycleOwner {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySecondScreenBinding
    private val viewModel: SecondScreenViewModel by viewModels { viewModelFactory }

    //    lateinit var recyclerView: RecyclerView
//    val adapter = QuotesRecyclerViewAdapter()
    lateinit var myRecyclerViewAdapter: QuotesRecyclerViewAdapter

    var layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_screen)
//        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
//        binding.lifecycleOwner = this
//        setContentView(binding.root)

        setupListeners()
        setupObservers()

        myRecyclerViewAdapter = QuotesRecyclerViewAdapter(
            this, mutableListOf(AuthorModel("", ""))
        )
        binding.quotesRecyclerV.layoutManager = layoutManager
        binding.myAdapter = myRecyclerViewAdapter
    }

    private fun setupListeners() {
        with(binding) {
            //secondScreenTV.text = viewModel.getTriggeredTing() ?: "Second Screen"
        }
    }

    private fun setupObservers() {
        viewModel.viewData.observe(this@SecondScreenActivity, Observer {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        })


// lifecycleScope.launch {} on its own dangerous, not LifeC aware!
        lifecycleScope.launch {
            // The block passed to repeatOnLifecycle is executed when the lifecycle
            // is at least STARTED and is cancelled when the lifecycle is STOPPED.
            // It automatically restarts the block when the lifecycle is STARTED again.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Safely collect from locationFlow when the lifecycle is STARTED
                // and stops collection when the lifecycle is STOPPED
                viewModel.quotesRecyclerState.collect {
                    renderState(viewModel.quotesRecyclerState.value)
                    Log.e("JIMMY219", "COllected from Act - $it")
                }
            }
        }
    }

    private fun renderState(state: SecondScreenViewModel.QuotesState) {
        if (state is SecondScreenViewModel.QuotesState.Loaded) {
            renderRecyclerView(state.quoteList)
        } else {
            when (state) {
//                SecondScreenViewModel.QuotesState.Error -> TODO("Render generic or invalidToken error")
                else -> {
                    // Do nothing.. ? Google "deffault empty state mvvm kotlin recycler view
                }
            }
        }
    }

    // So is approach correct w MVVM..? Idea is that data from D-layer is collected here
    private fun renderRecyclerView(content: QuoteList) {
//        val myRecyclerViewAdapter = QuotesRecyclerViewAdapter(this)
//        binding.myAdapter = myRecyclerViewAdapter

        val authorModel: MutableList<AuthorModel> = mutableListOf()
        content.results?.forEach {
            authorModel.add(AuthorModel(it.author, it.authorSlug))
        }
        myRecyclerViewAdapter.submitList(authorModel)
        layoutManager.scrollToPosition(0) // Might not be needed ?
    }
}