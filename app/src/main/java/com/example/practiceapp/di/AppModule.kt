package com.example.practiceapp.di

import com.example.data.services.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton // NOTE - A SINGLETON HERE MEANS THE O GRAPH/COMPONENT NEEDS TO BE MARKED AS SINGLETON TOO
    fun provideStateSingleton(): com.example.data.StateSingleton {
        return com.example.data.StateSingletonImpl() // https://stackoverflow.com/questions/2697783/what-does-program-to-interfaces-not-implementations-mean
    }

    // Todo use counter tracked in Application class to understand how many instances of this is being made..
    @Provides
    @Singleton
    fun provideBaseApi(): BaseApi {
        return BaseApiImpl()
    }

    @Provides
    @Singleton
    fun provideQuotesApi(baseApi: BaseApi): QuotesService {
        return QuotesApi(baseApi)
    }

    @Provides
    @Singleton
    fun provideQuotesRepository(quotesApi: QuotesApi): QuotesRepository {
        return QuotesRepositoryImpl(quotesApi)
    }

}