package com.example.data.services

import com.example.data.services.response.QuoteList
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import retrofit2.Response
import javax.inject.Inject

// Flows are suitable for domain layer as that layer should "ideally be platform-independent" - https://proandroiddev.com/should-we-choose-kotlins-stateflow-or-sharedflow-to-substitute-for-android-s-livedata-2d69f2bd6fa5
interface StateInRepository {
    val stateInValues: Flow<String>

    val exampleCache: Flow<String>
}

// Adapted from https://medium.com/androiddevelopers/things-to-know-about-flows-sharein-and-statein-operators-20e6ccb2bc74
// 1 of the main points of this/shareIn is that it improves performance - enabling the sharing of a single instance rather than creating several
class StateInRepositoryImpl @Inject constructor(
    private val quotesRepository: QuotesRepository, // Note - This is better off as a datasource
    private val externalScope: CoroutineScope
) : StateInRepository {
// So outsourced this shareIn() M to a repo so it actually works as intended.. Was acting funny when below was defined in VM!

    override val stateInValues: Flow<String> =
        quotesRepository.getJimmyMade().shareIn(externalScope, Eagerly, 1)
    // Eagerly & Lazily are low key v similar! What else makes them different? Lazily is about deferring init, so must be related
    // TODO Find more usecases to properly understand, when do ADD INFO HERE

    override val exampleCache: Flow<String> =
        quotesRepository.getJimmyMade().stateIn(externalScope, WhileSubscribed(), "DEFAULT_STATE")
    // Flow.stateIn caches and replays the last emitted item to new collectors

    // DO NOT USE shareIn or stateIn in a function like this.
    // It creates a new SharedFlow/StateFlow per invocation which is not reused!
//    fun getUser(): Flow<User> = // I.e. Use VALS like above and not funs
//        userLocalDataSource.getUser().shareIn(externalScope, WhileSubscribed())

}
