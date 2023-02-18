package com.example.secondscreenfeature.secondScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.practiceapp.repo.StateSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
//import services.QuotesApi
//import services.RetrofitHelper
//import services.TflApi
//import services.response.QuoteList
//import services.response.StopInfo
import javax.inject.Inject

// @Inject constructor
// (val userRepository: StateSingleton)
class SecondScreenViewModel() : ViewModel() {

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event: SharedFlow<Event> = _event

    private val _loadingState: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.Idle)
    val loadingState: StateFlow<LoadingState> = _loadingState



    lateinit var thisIs: String

//    var jimmysLiveData = MutableLiveData<QuoteList>()
//    var tflLiveData = MutableLiveData<Response<StopInfo>>()

//    private val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
//    private val tflApi = RetrofitHelper.getInstance().create(TflApi::class.java)

    var count = MutableLiveData<Int>()

    init {
        count.value = 0
    }

    fun getUpdatedCount() {
        count.value = (count.value)?.plus(1)
    }

    // TODO 1) Understand TFL, what is the response about..? Dont spend too much time on this but lets try and get some data on buses and what can be done
    //  do so in the future to draw as a compose
    //  2) Clean this shit up.. GEt rid of unneeded stuff!!! Like maybe keep counter  but also re-read all old URLs in comments to add comments here AND IN BOOK
//    suspend fun loadTfl(): MutableLiveData<Response<StopInfo>> = withContext(Dispatchers.IO) {
//        setLoading(true)
//
//        val result = tflApi.getStopInfo("490G00005781")
//
//        if (result.isSuccessful) {
//            Log.e(
//                "jimmy success", "${result.body()}"
//            ) // TODO "result" is services.response.StopInfo but most of it is null..? But at least detailed response from server works! Figure out wag1
//        } else {
//            Log.e("jimmy error1", "${result.body()}")
//            Log.e("jimmy error2", "${result.errorBody()}")
//        }
//
//        setLoading(false)
//
//        // Log.e("Jimmy", "${userRepository.getTriggeredTing()}")
//
//        tflLiveData.postValue(result)
//        tflLiveData
//    }

    private fun setLoading(isLoading: Boolean) {
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


//suspend fun loadJimmys(): MutableLiveData<List<String>> {
//    Log.e("jimmy", "jimmy Starting call")
//    setLoading(true) // This approach might now work
//
//    jimmysLiveData.postValue(listOf("Jimmy", "jimmy", "jimmy231"))
//    delay(3000L) // Despite printing a toast, as long as this suspend func is loaded on background T, it'll save the toast for after this delay completes
//
//    setLoading(false)
//
//
//    return jimmysLiveData
//}

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