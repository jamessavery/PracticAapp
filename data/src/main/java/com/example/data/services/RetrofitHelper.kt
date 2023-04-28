package com.example.data.services

import com.example.data.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitHelper {

    private const val BASE_QUOTE_URL = "https://quotable.io/"
    private const val TFL_BASE = "https://api.tfl.gov.uk/"

    private var logging: HttpLoggingInterceptor = HttpLoggingInterceptor()

// TODO https://stackoverflow.com/questions/61729790/retrofit-singleton-in-kotlin
    fun getInstance(): Retrofit {
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(BASE_QUOTE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            // Need to add converter factory to convert JSON object to Java object
            .client(httpClient.build())
            .build()
    }
}