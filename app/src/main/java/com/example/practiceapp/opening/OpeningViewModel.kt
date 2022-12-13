package com.example.practiceapp.opening

import services.response.StopInfo
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import services.QuotesApi
import services.RetrofitHelper
import services.TflApi

class OpeningViewModel() : ViewModel() {

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event: SharedFlow<Event> = _event

    private val _loadingState: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.Idle)
    val loadingState: StateFlow<LoadingState> = _loadingState

    var jimmysLiveData = MutableLiveData<List<String>>()

    private val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
    private val tflApi = RetrofitHelper.getInstance().create(TflApi::class.java)

    //TODO 1) Progress on TFL, get data DL'd properly so next step would be to draw result on screen (Got an idea from fucking blue response, but cant seem to match it on red (local POJO)
    //  2) Try and get live data working! See how it works for the count example.. FUCK handler that shit deprecated anyways.. Just get it working w TFL Tings (Or use old Live data tutorial which has Handler)
    //  3) Clean this shit up.. GEt rid of unneeded stuff!!! Like counter can go once usual live data shit is DONE! Also gonna want some notes for live data so skim over old shit..?
    var count = MutableLiveData<Int>()
    init {
        count.value = 0
    }

    fun getUpdatedCount() {
        count.value = (count.value)?.plus(1)
    }

    // Maybe when flow is working, find another LiveData tutorial..?
    // Todo call new API here, do as per tutorial at first but convert to MutableLIveData..?
//    fun loadJimmys(): MutableLiveData<List<String>> {
//        jimmysLiveData = MutableLiveData()
//        val handler = Handler()
//        Log.e("jimmy", "jimmy Starting call")
//        setLoading(true) // This approach might now work
//
//        viewModelScope.launch {
//            handler.postDelayed({
//            Runnable {
//                Log.e("jimmy", "jimmy IN call")
//                val listOfJimmys: List<String> = listOf("jimmy1", "jimmy2", "jimmy3", "jimmy4", "jimmy5")
//                listOfJimmys.shuffled()
//                jimmysLiveData.value = listOfJimmys
//            }
//        }, 3000)
//        }
//
//        setLoading(false)
//
//        return jimmysLiveData
//    }

    suspend fun loadTfl(): Response<StopInfo> = withContext(Dispatchers.IO) {
        setLoading(true)

        val result = tflApi.getStopInfo("490G00005781")

        Log.e("jimmy", "before timer") // TODO Draw results onto screen ?
        if(result.isSuccessful) {
            Log.e("jimmy success", "${result.body()}") // TODO "result" is services.response.StopInfo but most of it is null..? But at least detailed response from server works! Figure out wag1
        } else {
            Log.e("jimmy error1", "${result.body()}")
            Log.e("jimmy error2", "${result.errorBody()}")
        }
        setLoading(false)
        Log.e("jimmy", "after timer")
        result
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

//        handler.postDelayed({
//            Runnable {
//                Log.e("jimmy", "jimmy IN call")
//                val listOfJimmys: List<String> = listOf("jimmy1", "jimmy2", "jimmy3", "jimmy4", "jimmy5")
//                listOfJimmys.shuffled()
//                jimmysLiveData.value = listOfJimmys
//            }
//        }, 3000)
// It'd be nice for this to work

//    suspend fun loadJimmys(): Response<QuoteList> = withContext(Dispatchers.IO) {
//        setLoading(true)
//        val result = quotesApi.getQuotes()
//        Log.e("jimmy", "before timer")
//        delay(3000L)
//        if(result.isSuccessful) {
//            _event.emit(Event.CloseBottomSheet)
//        } else {
//            _event.emit(Event.CloseBottomSheet)
//        }
//        setLoading(false)
//        Log.e("jimmy", "after timer")
//        Log.e("jimmy", "${result.body()}")
//
//        result
//    } // THIS BASIC IMPL WORKED FIRST TIME