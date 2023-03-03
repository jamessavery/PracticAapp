package com.example.data.services

import android.util.Log
import com.example.data.services.response.QuoteList
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface QuotesRepository {
    suspend fun getQuotes(): Observable<QuoteList>
}
// FINISH W - https://proandroiddev.com/flexible-recyclerview-adapter-with-mvvm-and-data-binding-74f75caef66a
class QuotesRepositoryImpl @Inject constructor(private val quotesApi: QuotesApi) : QuotesRepository {

    override suspend fun getQuotes(): Observable<QuoteList> {
        return quotesApi.getQuotes()
    }

}
