package com.example.data.services

import io.reactivex.rxjava3.core.Observable
import com.example.data.services.response.QuoteList
import retrofit2.Response
import retrofit2.http.GET

interface QuotesApi {
    @GET("/quotes")
    fun getQuotes() : Observable<QuoteList>
}