package com.example.practiceapp.di

import android.app.Application
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

//    @Provides
//    @Singleton
//    fun providePreferencesDataStore( // For noting purposes, this is for anything that requires context I.e SQL
//        applicationContext: Application // NOTE - For this purpose, application context works perfectly! Just remember memory leaks/exception when used in scenario req'ing activity context!
//    ): PreferencesDataStore {
//        return PreferencesDataStoreImpl(applicationContext)
//    }

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