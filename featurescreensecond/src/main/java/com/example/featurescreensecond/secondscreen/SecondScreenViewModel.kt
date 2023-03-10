package com.example.featurescreensecond.secondscreen

import android.util.Log
import androidx.lifecycle.*
import com.example.data.StateSingleton
import com.example.data.services.*
import com.example.data.services.response.QuoteList
import com.example.data.services.response.StopInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SecondScreenViewModel @Inject constructor(
    private val userRepository: StateSingleton,
    private val quotesRepository: QuotesRepository
    ) : ViewModel() {

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event: SharedFlow<Event> = _event

    private val _loadingState: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.Idle)
    val loadingState: StateFlow<LoadingState> = _loadingState

    // Todo should probs use SoT here (If thats what I think it is), fix later..
    val ting get() = quotesRepository.getQuotes() // .asLiveData() can be done, but is collected by calling .observer(), which is deprecated! State/Shared better!
                                            // shareIn(
    // scope =
    // ) is used to transform flow -> SharedFlow
    // About halfway, F "shareIn(" - https://proandroiddev.com/should-we-choose-kotlins-stateflow-or-sharedflow-to-substitute-for-android-s-livedata-2d69f2bd6fa5

    private val _showSnackBar = MutableLiveData(false) // TODO jimmy Should the state of this be in the VM and RX like this?!
    val showSnackBar: LiveData<Boolean>
        get() = _showSnackBar

    // TODO ----> https://proandroiddev.com/flexible-recyclerview-adapter-with-mvvm-and-data-binding-74f75caef66a
    val viewData: LiveData<QuoteList>
        get() = _viewData
    private val _viewData = MutableLiveData<QuoteList>()

    init {
        loadData()
    }

    private fun loadData() {
        // This is a coroutine scope with the lifecycle of the ViewModel
        viewModelScope.launch {
            val quotesList: QuoteList
            // getCarListData() is a suspend function
            quotesRepository.getQuotes().collect {
                postData(it)
            }
        }
    }

    private fun postData(quoteList: QuoteList) {
        println("JIMMY219- ${quoteList}")
        _viewData.postValue(quoteList)
    }

    lateinit var thisIs: String

    var jimmysLiveData = MutableLiveData<QuoteList>()
    var tflLiveData = MutableLiveData<Response<StopInfo>>()

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
    suspend fun loadTfl(): MutableLiveData<Response<StopInfo>> = withContext(Dispatchers.IO) {
        setLoading(true)

        //val result = tflApi.getStopInfo("490G00005781")

//TODO Create repo for TFL Api

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
//        tflLiveData.postValue(result)
        tflLiveData
    }

    private fun setLoading(isLoading: Boolean) {
        viewModelScope.launch {
            if (isLoading) {
                _loadingState.emit(LoadingState.Loading)
            } else {
                _loadingState.emit(LoadingState.Idle)
            }
        }
    }

    fun getTriggeredTing(): String? { // This is a getter, so it ISNT reactive! It should be observing so it auto gets it
        val ting = userRepository.getTriggeredTing()
        Log.e("jimmy", "JIMMY $ting at SecondSCreen")
        return ting
    }

    sealed class Event {
        object OpenBottomSheet : Event()
        object CloseBottomSheet : Event()
    }

    sealed class LoadingState {
        object Idle : LoadingState()
        object Loading : LoadingState()
    }

    // Define ViewModel factory in a companion object
//    companion object {
//        fun provideFactory(
//            myRepository: StateSingletonImpl,
//            owner: SavedStateRegistryOwner,
//            defaultArgs: Bundle? = null,
//        ): AbstractSavedStateViewModelFactory =
//            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
//                @Suppress("UNCHECKED_CAST")
//                override fun <T : ViewModel> create(
//                    key: String,
//                    modelClass: Class<T>,
//                    handle: SavedStateHandle
//                ): T {
//                    return SecondScreenViewModel(myRepository) as T
//                }
//            }
//
//        // Shorter version..?
//        class ViewModelFactory(private val counter: Int) : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return SecondScreenViewModel(StateSingletonImpl()) as T
//            }
//        }
//    }

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