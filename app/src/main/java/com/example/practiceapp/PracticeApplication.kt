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

class PracticeApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .factory()
            .create(this)
        //return DaggerAppComponent.builder() // This is an older way of going about things, .factory() is newer & less verbose
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

    // Note - You can only have 1 of this -
    // (this being Factory or Builder)
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent //@BindsInstance here makes application available app-wide, used for appContext
    }

    // Tells Dagger that OpeningActivity requests dependencies, so the graph needs to
    // provide/satisfy them to the fields ((F injection?)) that LoginActivity is requesting
//    fun inject(activity: OpeningActivity)

    // No longer needed with Android support library approach! Now uses ActivityBuildersModule
//    fun inject(activity: SecondScreenActivity)
}




