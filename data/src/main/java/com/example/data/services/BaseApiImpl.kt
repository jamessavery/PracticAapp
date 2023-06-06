package com.example.data.services

import android.util.Log
import com.example.data.services.response.QuoteList
import retrofit2.Response
import javax.inject.Singleton

class BaseApiImpl() : BaseApi {

    override val quotesApi: QuotesService = RetrofitHelper.getInstance().create(QuotesService::class.java)

    override val quotesRxApi: QuotesService = RxRetrofitHelper.retrofitRxInstance.create(QuotesService::class.java)

    override val disneyApi: QuotesService = RetrofitHelper.getInstance().create(QuotesService::class.java)


}

interface BaseApi {

    val quotesApi: QuotesService

    val quotesRxApi: QuotesService

    val disneyApi: QuotesService

}