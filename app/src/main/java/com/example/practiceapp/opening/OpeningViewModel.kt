package com.example.practiceapp.opening

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practiceapp.model.SendingToEmailResultEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.core.Observable.interval
import io.reactivex.rxjava3.disposables.CompositeDisposable

import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.internal.operators.observable.ObservableBuffer
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import services.QuotesApi
import services.RetrofitHelper
import services.RxRetrofitHelper
import services.TflApi
import services.response.QuoteList
import services.response.Result
import services.response.StopInfo
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class OpeningViewModel @Inject constructor(
//    private val userRepository: SendingToEmailResultEntity
    ) : ViewModel() {

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event: SharedFlow<Event> = _event

    private val _loadingState: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.Idle)
    val loadingState: StateFlow<LoadingState> = _loadingState

    private val _secondScreenState: MutableStateFlow<SecondScreenState> = MutableStateFlow(SecondScreenState.Default)
    val secondScreenState: StateFlow<SecondScreenState> = _secondScreenState

    lateinit var thisIs: String

    var jimmysLiveData = MutableLiveData<QuoteList>()
    var tflLiveData = MutableLiveData<Response<StopInfo>>()

    private val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
    private val tflApi = RetrofitHelper.getInstance().create(TflApi::class.java)
    private val quotesRxApi = RxRetrofitHelper.retrofitRxInstance.create(QuotesApi::class.java)

    var count = MutableLiveData<Int>()

    init {
        count.value = 0
    }

    fun getUpdatedCount() {
        count.value = (count.value)?.plus(1)
    }

    suspend fun loadRxJimmy(): Observable<QuoteList>? {
        setLoading(true)

        Log.e("jimmy", "before timer")
        delay(2000L)
        quotesRxApi.getQuotes().doOnSubscribe {
            setLoading(true)
        }.doOnTerminate {
            setLoading(false)
        }.flatMap {
            Observable.fromIterable(it.results) // fromIterable() processes this as a list, but it is done via 1by1 below
        } // Note - fromArray is more processing it 1 by 1, rather than as a list?
            // .toList() // fromITerable() turns into a list, but it is returned 1ataTime below, uncommenting toList() turns it into a list
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                println("Jimmy data - $data")
            }, { exception ->
                Log.e("jimmy", "Exception - ${exception.message}")
            })

        Log.e("jimmy", "after timer")


        return null
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

    sealed class SecondScreenState() {
        object Default : SecondScreenState()
        object Triggered : SecondScreenState()
    }

    // RX Syntax reminder
//    Observable.fromIterable(listOf("Apple", "Orange", "Banana"))
//            .subscribe(
//                { value -> println("Received: $value") }, // onNext
//                { error -> println("Error: $error") },    // onError
//                { println("Completed") })                 // onComplete
    // So it applies .map() which is formatting and returning back to the stream
    fun mapExampleCode() {
        val toFlatten: String = "Lorem Ipsum is simply dummy text"
        val wordsToFlatten = toFlatten.split(" ")
        Observable.fromIterable(wordsToFlatten)// Note there is fromIterable() & fromArray()
            .map { word ->
                val alsoThis = SendingToEmailResultEntity(
                    word, false
                )
                Log.e("jimmy", "$word -> ${word.length} letters. ALSO - $alsoThis ")
            }.subscribe({ onSuccess -> Log.e("jimmy", "Map result - $onSuccess") })
    }

    // Compared to above, .flatMap() transforms items into observables(O), then flattens said Os into a single O
    // Using .split() below splits it twice to indicate how the O returns, see -
    // <img width="640" height="226" src="https://www.niraj.life/images/rx-java-flatmap.png" alt="">
    // Notice how it splits the words, then the words into letters, and puts said letters into a single O
    // So just having 1 O means we can have multiple calls in 1 flow
    fun flatMapExampleCode(): Observable<String> {
        val toFlatten: String = "Lorem Ipsum is simply dummy text"
        val wordsToFlatten = toFlatten.split(" ")
        return Observable.fromIterable(wordsToFlatten) // Observable emits a list, below deals w its emissions
            .flatMap { word -> // Main diff between flatMap & map is that flatMap has to return another observable
                //printThatShit(Observable.fromIterable(word.split(" "))) // NOTE - Attempted this but realised cant handle observable here. Has to be done in subscribe bit, this is last bit of 3 part RX process (Observer)
                Observable.fromIterable(word.split(" ")) // It 'flattens' it into 1 observable
                    .onErrorReturn {
                        "it" // You can also map results from here into a model/POJO, or use this to return any errors
                    }
            }
        //.subscribe({ onNext -> Log.e("Jimmy", onNext) }, { onError -> }) // For calling on activity
        // SO as can be seen above, the flatMap() transforms the observable by applying a function to each item (in this case, .split())
        // Said function returns Os, which is then FLATTENED into 1 O. Remember its useful for combining functions but it doesnt take into account order (returned Os are not in the correct order)
        // Can be used to send messages to addresses, in no particular order (otherwise we should use concatMap which preserves the order of the elements)
    }
    // Similar to above, a switchMap works exactly the same BUT only retains the results of the latest O, discarding previous ones
    // I.e. a search engine might use switchMap, as it attempts searches w every letter but will discard every old search and show the latest

    // Doesnt work, use .subscribe() to actually process the contents of observable
    private fun printThatShit(fromIterable: Observable<String>?): Observable<String>? {
        fromIterable?.map {
            Log.e("Jimmy", it)
        }
        return fromIterable
    }

    fun finalExampleRXWithAllAboveTings() {
        Observable.fromIterable(
            listOf("email", "addresses", "that", "Ima", "send", "spam", "too")
        ) // Gets list of emails we wanna send emails to
            .flatMap { address -> // USing flatmap() Calls below M, as flatmap returns a single O by squashing multiple Os
                sendIndividualEmailObservable(address).map { // This returns a single O, but calling w flatMap() invokes it multiple times..
                    SendingToEmailResultEntity(
                        address,
                        true
                    ) // Changes outcome of this O chain to be this data type! Rather than just an O w each String / outcome of sendRequisutesToEmail()
                }
                    .onErrorReturn { // ON cases where its an error, invokes below and continues w rest of emails!
                        SendingToEmailResultEntity(address, false)
                    }
                // .flatMap { alsoSendSpamToEmails(address) } // Note - You can also chain flatMaps for Diff methods,
                // or use concat() to chain the output of multiple Os & flatten (but preserves order)
                // Compared to .concat(), .merge() allows interleaving, but concat doesn't (preserves order)
                // flatMap() has merging of Os within predicate. merge, concat & zipWith are done via static means / passed in param
            } // Note - zipWIth() Attempts to run observables in parallel, whereas flatMap() does not
            .zipWith( // A new req I made was to emit each item/email from the list, once a second. Initially thought that interval had to be at start (above)
                Observable.interval( // But that made it emit the ENTIRE list every second, instead of individual items
                    1000L, // Then I REALISED interval() here is a O just like fromIterable is!! So this is how zip/zipWith is used! YOU CHAIN 2 Os!
                    TimeUnit.MILLISECONDS // Os are a datasource and even a emitter of sorts! Its anything relating to data coming from somewhere!!
                ) // Not too sure how to do this w/o predicate, was struggling w params for a while (below line)
            ) { emailEntity: SendingToEmailResultEntity, interval: Long ->
                emailEntity // -> arrow indicates start of lambda, use next line to actually compose what happens

            } // But of the BiFunction, there has to be 2 passed variables, from each of the Os!
            .filter { it.success } // If NOT successful, then map the results/list of failing emails into a list below // Note I reversed this to test below
            .map {
                println("jimmy individualFailedAddresses - $it")
                it.address
            }.toList() // Maps failing emails into a list
            .subscribe { listOfFailedAddresses -> // Said list of failing emails as a list, pass into a method
                println("jimmy listOfFailedAddresses - $listOfFailedAddresses")
                // handleSendRequestResult(listOfFailedAddresses) // Handles failing emails, successful emails are handled above/doesnt really need to be handled
            }
    }

    private fun sendIndividualEmailObservable(address: String) = Observable.just("email") // Dummy M

    private fun flowBackPressureExample() {

        // PublishSubject is a iterator of sorts. So for Strings/Chars, for loop below would be - "asfa".forEach { backPressureExample.onNext(it.toString()) }
        val backPressureExample = PublishSubject.create<Int>()
        backPressureExample.observeOn(Schedulers.computation())
            .toFlowable(BackpressureStrategy.DROP) // W/o Flowable, it'll eventually OOM (if device not top notch)
            .subscribe(
                {
                    println("The Number Is: $it")
                },
                {
                    print(it.message)
                }
            )
        for (i in 0..1000000) {
            backPressureExample.onNext(i)
        }

        // THeres also these, which emits data at point of being subscribed to
        var flowableOnSubscribe = FlowableOnSubscribe { flowable -> flowable.onNext(1) }
        var integerFlowable = Flowable.create(flowableOnSubscribe, BackpressureStrategy.BUFFER)
    }

    fun testThis(): Observable<Int> {
        return Observable.range(1, 5)
            .filter { n -> n > 1 }
            .zipWith(
                Observable.interval(50, TimeUnit.MILLISECONDS),
                BiFunction { t1, t2 -> // Initially just used interval() straight here (instead of zipWith(x)). This made a wait before emitting a list. ZIPWITH ACTUALLY COMBINES IT, CONCURRENTLY
                    t1
                })
            .doOnNext { item ->
                System.out.println("jimmy - ${System.currentTimeMillis()}")
                System.out.println("jimmy - $item\n")
            }

        // https://www.geeksforgeeks.org/understanding-rxjava-timer-delay-and-interval-operators/
        // Observable.timer is used to pass time x seconds then move onto next task!
        // Observable.interval(x,y,z) ((Can take 3, not just 2, for initial delay)) -> THIS POTENTIALLY CONTINUES INDEFINITELY- repeating what its called on
        // Calling dispose() (on take(n) and compositeDisposable can clear it
//        Observable.create<String> { emitter -> // Create can be v useful for defining our own onNext() & onComplete() values
//            emitter.onNext("GeeksforGeeks")
//            emitter.onComplete()
//        }.subscribeOn(Schedulers.io()) // Use below when wish to execute the work first, then postpone the emission for a specific amount of time
//            .delay(10, TimeUnit.SECONDS) // This'll advance time by 10 seconds, so that the emitted onNext() value above can only be delivered after time
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()

    }

    fun practiceRx() {
        // One could assume that assigning both to be subbed on .io() means it'll be diff Ts (as it works via dynamic changing of Ts)
//        Observable.zip(
//            quotesRxApi.getQuotes().subscribeOn(Schedulers.io()),
//            quotesRxApi.getQuotes().subscribeOn(Schedulers.io()),
//            BiFunction{
//                    firstResonse: QuoteList,
//                    secondResponse: QuoteList ->
//                listOf<Result>(firstResonse.results.first(), secondResponse.results.first()) })
//        .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { it -> Log.e("Jimmy", "Jimmy $it") }

        // This allows the separate processing of Os of which results are literally concatted into a list, see final example where this didnt work w concatMap
        val observable1 = quotesRxApi.getQuotes()
        val observable2 = quotesRxApi.getQuotes()
        Observable.concat(observable1, observable2)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe {
                Log.e("JimmySuccess", it.results?.first().toString())
            } // THIS WORKS EXACTLY AS INTENDED!

        // BELOW doesnt allow the passing of both Os into the list that gets printed
//        val observable = quotesRxApi.getQuotes()
//            .subscribeOn(Schedulers.io())
//            .concatMap {
//                val ting = quotesRxApi.getQuotes().subscribeOn(Schedulers.io()).flatMap {
//                    updateTheTing(it.results?.first() as Result)
//                    Observable.just(it)
//                }.subscribe()
//                    //.blockingFirst() // Cant make another call within a call without blocking the calling T first / pausing UI..?
//                Observable.just(it)
//                //Observable.fromIterable(listOf(it, ting.blockingFirst()))
//            }
//            .observeOn(AndroidSchedulers.mainThread()).subscribe {
//                println("Jimmy choochoo ${it?.results?.first()}")
//            }
    }

    fun practiceCOmpositeRx() {
        val compositeDisposable: CompositeDisposable = CompositeDisposable() // TODO practice w this
        val disposable = Observable.just("ASd").subscribe()

        compositeDisposable.add(disposable)
    }

    private fun removeThis(): Observable<QuoteList> {
        return Observable.just(QuoteList(results = listOf(services.response.Result("ASD", "ASD"))))
    }

    private fun updateTheTing(it: Result) {
        println("Jimmy choochoochoo $it")
    }

    fun sendTriggeredState() {
        _secondScreenState.value = SecondScreenState.Triggered
    }

}

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