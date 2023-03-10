package com.example.data.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {

    private const val BASE_QUOTE_URL = "https://quotable.io/"
    private const val TFL_BASE = "https://api.tfl.gov.uk/"

    private var logging: HttpLoggingInterceptor = HttpLoggingInterceptor()


    fun getInstance(): Retrofit {
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(BASE_QUOTE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            // Need to add converter factory to convert JSON object to Java object
            .client(httpClient.build())
            .build()
    }
}