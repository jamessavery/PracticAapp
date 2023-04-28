package com.example.data.services

import io.reactivex.rxjava3.core.Observable
import com.example.data.services.response.QuoteList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class QuotesApi @Inject constructor(private val baseApi: BaseApi) : QuotesService {

    override fun getRxQuotes(): Observable<QuoteList> {
        return baseApi.quotesRxApi.getRxQuotes()
    }

    override suspend fun getQuotes(): QuoteList {
        return baseApi.quotesApi.getQuotes()
    }

    override suspend fun getResponseQuotes(): Response<QuoteList> {
        return baseApi.quotesApi.getResponseQuotes()
    }

}


// By default, kotlin const are like QuotesApi() - This is a primary const. IS FOR INIT (initing the class!)
// Doing it like "QuotesApi constructor()" is a secondary constructor. This is more for DEFINING purposes
// Also adding val basically creates a field for it. As it saves hastle of going "private val value = constValue" later on
// Wo creating a field for it, only super const (": MySuperclass(constValue) {" ) & init { constValue } can access said var!