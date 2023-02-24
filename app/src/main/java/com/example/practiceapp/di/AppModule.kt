package com.example.practiceapp.di

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

}