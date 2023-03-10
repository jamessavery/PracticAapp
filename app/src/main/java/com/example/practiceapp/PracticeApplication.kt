package com.example.practiceapp

import android.app.Application
import com.example.practiceapp.di.ActivityBuildersModule
import com.example.practiceapp.di.AppModule
import com.example.practiceapp.utils.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.*
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

// TODO finish notes on below DI approach - https://medium.com/@shashankmohabia/dagger-android-with-mvvm-dependency-injection-for-android-3a7e33ad1013
class PracticeApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        ViewModelModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<PracticeApplication> { // HAS TO BE CALLED APPCOMPONENT

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    // Tells Dagger that OpeningActivity requests dependencies, so the graph needs to
    // provide/satisfy them to the fields ((F injection?)) that LoginActivity is requesting
//    fun inject(activity: OpeningActivity)

    // No longer needed with Android support library approach! Now uses ActivityBuildersModule
//    fun inject(activity: SecondScreenActivity)
}




