package com.example.practiceapp

import android.app.Application
import com.example.data.StateSingletonImpl
import com.example.featurescreensecond.SecondScreenActivity
import com.example.featurescreensecond.di.SecondScreenComponent
import com.example.practiceapp.opening.OpeningActivity
import dagger.Component
import dagger.Module
import dagger.Provides

class PracticeApplication : Application() {

    val appComponent = DaggerApplicationComponent.create()

}

//, SecondScreenModule::class
@Component(modules = [AppModule::class, SecondScreenModule::class])
interface ApplicationComponent {

    // Tells Dagger that OpeningActivity requests dependencies, so the graph needs to
    // provide/satisfy them to the fields ((F injection?)) that LoginActivity is requesting
    fun inject(activity: OpeningActivity)

    fun inject(activity: SecondScreenActivity)

//    fun inject(practiceApplication: PracticeApplication)

//    fun secondScreenComponent(): SecondScreenComponent.Factory

}

@Module
class AppModule {

    @Provides
    fun provideStateSingleton(): com.example.data.StateSingletonImpl {
        return com.example.data.StateSingletonImpl()
    }

//    @Provides
//    fun provideViewModelFactory(): ViewModelFactory {
//        return ViewModelFactory()
//    }

}