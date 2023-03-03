package com.example.data.services

import android.util.Log
import javax.inject.Singleton

class BaseApiImpl() : BaseApi {

    override val quotesRxApi: QuotesService = RxRetrofitHelper.retrofitRxInstance.create(QuotesService::class.java)

}

interface BaseApi {

    val quotesRxApi: QuotesService

}