package com.example.data.services

import com.example.data.services.response.QuoteList
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface QuotesRepository {
    suspend fun getRxQuotes(): Observable<QuoteList>
    fun getQuotes(): Flow<QuoteList>
}
// FINISH W - https://proandroiddev.com/flexible-recyclerview-adapter-with-mvvm-and-data-binding-74f75caef66a
class QuotesRepositoryImpl @Inject constructor(private val quotesApi: QuotesApi) : QuotesRepository {

    override suspend fun getRxQuotes(): Observable<QuoteList> {
        return quotesApi.getRxQuotes()
    }

    override fun getQuotes(): Flow<QuoteList> = flow {
        val quotes = quotesApi.getQuotes()
        emit(quotes)
    }
//    {
//        return quotesApi.getQuotes()
//    }


}
