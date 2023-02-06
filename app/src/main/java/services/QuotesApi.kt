package services

import io.reactivex.rxjava3.core.Observable
import services.response.QuoteList
import retrofit2.Response
import retrofit2.http.GET

interface QuotesApi {
    @GET("/quotes")
    fun getQuotes() : Observable<QuoteList>
}