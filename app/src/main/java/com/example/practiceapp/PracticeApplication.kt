package com.example.practiceapp

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.data.StateSingletonImpl
import com.example.featurescreensecond.SecondScreenActivity
import com.example.featurescreensecond.SecondScreenViewModel
import com.example.featurescreensecond.SecondScreenViewModel_Factory
import com.example.practiceapp.opening.OpeningActivity
import com.example.practiceapp.utils.ViewModelFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class PracticeApplication : DaggerApplication() {

    val appComponent = DaggerApplicationComponent.create()

    lateinit var stateSingletonImpl: StateSingletonImpl

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        TODO("Not yet implemented")
    }

}

//, SecondScreenModule::class
@Component(modules = [AppModule::class])
interface ApplicationComponent {

    // Tells Dagger that OpeningActivity requests dependencies, so the graph needs to
    // provide/satisfy them to the fields ((F injection?)) that LoginActivity is requesting
    fun inject(activity: OpeningActivity)

    fun inject(activity: SecondScreenActivity)

//    fun inject(practiceApplication: PracticeApplication)

//    fun SecondScreenComponent(): SecondScreenComponent.Factory

}

@Module
class AppModule {

    @Provides
    fun provideStateSingleton(): com.example.data.StateSingletonImpl {
        return com.example.data.StateSingletonImpl()
    }

}