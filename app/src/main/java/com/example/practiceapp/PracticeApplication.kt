package com.example.practiceapp

import android.app.Application
import com.example.practiceapp.opening.OpeningActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

class PracticeApplication : Application() {

    //val appComponent = DaggerApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()
    }

}

@Component
interface ApplicationComponent {

    // Tells Dagger that OpeningActivity requests dependencies, so the graph needs to
    // provide/satisfy them to the fields ((F injection?)) that LoginActivity is requesting
    fun inject(activity: OpeningActivity)

//    fun inject(activity: SecondScreenActivity)

}

@Module
class AppModule {
//
//    @Provides
//    @Singleton
//    fun provideSomeSingleton() {
//
//    }

}