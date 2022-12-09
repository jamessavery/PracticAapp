package com.example.practiceapp.OpeningScreen

import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OpeningViewModel() : ViewModel() {

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event: SharedFlow<Event> = _event

    private val _loadingState: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.Idle)
    val loadingState: StateFlow<LoadingState> = _loadingState

    var jimmysLiveData = MutableLiveData<List<String>>()

    //TODO THIS WORKS, CONTRAST BOTH LIVE DATA APPROACHES TO UNDERSTAND WHY INITIAL GO ISNT WORKING
    var count = MutableLiveData<Int>()
    init {
        count.value = 0
    }

    fun getUpdatedCount() {
        count.value = (count.value)?.plus(1)
    }

    fun loadJimmys(): MutableLiveData<List<String>> {
        jimmysLiveData = MutableLiveData()
        //setLoading(true) // This approach might now work

        val handler = Handler()
        Log.e("jimmy", "jimmy Starting call")
//        handler.postDelayed({
//            Runnable {
//                Log.e("jimmy", "jimmy IN call")
//                val listOfJimmys: List<String> = listOf("jimmy1", "jimmy2", "jimmy3", "jimmy4", "jimmy5")
//                listOfJimmys.shuffled()
//                jimmysLiveData.value = listOfJimmys
//            }
//        }, 3000)

        //setLoading(false)
        // Use this guide for now
        val listOfJimmys: List<String> = listOf("jimmy1", "jimmy2", "jimmy3", "jimmy4", "jimmy5")
        listOfJimmys.shuffled()
        jimmysLiveData.value = listOfJimmys
        Log.e("jimmy", "jimmy2 - ${jimmysLiveData.toString()})")
        return jimmysLiveData // TODO here is where it crashes
        // .asLiveData() use this
        // USE THIS https://medium.com/@taman.neupane/basic-example-of-livedata-and-viewmodel-14d5af922d0
    }

    fun onBottomSheetButtonClicked(toggled: Boolean) {
        viewModelScope.launch {
            if (toggled) {
                _event.emit(Event.CloseBottomSheet)
            } else {
                _event.emit(Event.OpenBottomSheet)
            }
        }
    }

    // This doesnt completely work but debug another time
    fun setLoading(isLoading: Boolean) {
        Log.e("setLoadingResult", isLoading.toString())
        viewModelScope.launch {
            if (isLoading) {
                _loadingState.emit(LoadingState.Loading)
            } else {
                _loadingState.emit(LoadingState.Idle)
            }
        }
    }

    sealed class Event {
        object OpenBottomSheet : Event()
        object CloseBottomSheet : Event()
    }

    sealed class LoadingState {
        object Idle : LoadingState()
        object Loading : LoadingState()
    }

}