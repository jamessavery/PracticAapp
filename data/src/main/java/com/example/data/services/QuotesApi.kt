package com.example.data.services

import io.reactivex.rxjava3.core.Observable
import com.example.data.services.response.QuoteList
import javax.inject.Inject

class QuotesApi @Inject constructor(private val baseApi: BaseApi) : QuotesService {

    override fun getQuotes(): Observable<QuoteList> {
        return baseApi.quotesRxApi.getQuotes()
    }

}


// By default, kotlin const are like QuotesApi() - This is a primary const. IS FOR INIT (initing the class!)
// Doing it like "QuotesApi constructor()" is a secondary constructor. This is more for DEFINING purposes
// Also adding val basically creates a field for it. As it saves hastle of going "private val value = constValue" later on
// Wo creating a field for it, only super const (": MySuperclass(constValue) {" ) & init { constValue } can access said var!