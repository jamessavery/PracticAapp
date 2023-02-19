package com.example.data.services

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class QuotesRepository(private val quotesApi: QuotesApi) {


    suspend fun getQuotes() {
        quotesApi.getQuotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                println("Jimmy data - ::: $data")
            }, { exception ->
                Log.e("jimmy", "Exception :: ${exception.message}")
            })
    }

}