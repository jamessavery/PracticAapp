package com.example.practiceapp

import android.app.Application
import com.example.featurescreensecond.SecondScreenActivity
import com.example.practiceapp.opening.OpeningActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

class PracticeApplication : Application() {

    val appComponent = DaggerApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()
    }

}

//@Component(modules = [SecondScreenModule::class])
@Component(modules = [AppModule::class])
interface ApplicationComponent {

    // Tells Dagger that OpeningActivity requests dependencies, so the graph needs to
    // provide/satisfy them to the fields ((F injection?)) that LoginActivity is requesting
    fun inject(activity: OpeningActivity)

    fun inject(activity: SecondScreenActivity)

//    fun SecondScreenComponent(): SecondScreenComponent.Factory

}

@Module
class AppModule {

    @Provides
//    @Singleton // THIS CANT BE PUT HERE?  https://stackoverflow.com/questions/53876311/error-dagger-incompatiblyscopedbindings-unscoped-may-not-reference-scoped-b
    fun provideStateSingleton(): com.example.data.StateSingletonImpl {
        return com.example.data.StateSingletonImpl()
    }

//    @Provides
//    @Singleton
//    fun provideSomeSingleton(ting: Boolean): Boolean {
//        return ting
//    }

}