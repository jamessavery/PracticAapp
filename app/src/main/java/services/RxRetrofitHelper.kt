package services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RxRetrofitHelper {

    private const val BASE_QUOTE_URL = "https://quotable.io/"
    private const val TFL_BASE = "https://api.tfl.gov.uk/"

    lateinit var retrofit: Retrofit

    val retrofitRxInstance: Retrofit
        get() {
            if (!this::retrofit.isInitialized) {
//                val headersInterceptor = HttpLoggingInterceptor { chain ->
//                    val requestBuilder = chain.request.newBuilder()
//                    chain.proceed(requestBuilder.build())
//                }
                val okHttpClient = OkHttpClient()
                    .newBuilder()
                    .followRedirects(true)
//                    .addInterceptor(headersInterceptor)
                    .build()
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_QUOTE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit
        }
}