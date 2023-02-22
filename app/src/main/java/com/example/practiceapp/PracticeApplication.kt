package com.example.practiceapp

import android.app.Application
import com.example.featurescreensecond.SecondScreenActivity
import com.example.practiceapp.opening.OpeningActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.*
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Inject
import javax.inject.Singleton

// , HasAndroidInjector
class PracticeApplication : DaggerApplication() {
//
//    @Inject
//    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

   // override fun androidInjector() = dispatchingAndroidInjector

}

// ViewModelFactory.ViewModelFactory::class
//, SecondScreenModule::class,
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuildersModule::class, AppModule::class])
interface AppComponent : AndroidInjector<PracticeApplication> { // HAS TO BE CALLED APPCOMPONENT

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    // Tells Dagger that OpeningActivity requests dependencies, so the graph needs to
    // provide/satisfy them to the fields ((F injection?)) that LoginActivity is requesting
//    fun inject(activity: OpeningActivity)
//
//    fun inject(activity: SecondScreenActivity)

//    fun inject(practiceApplication: PracticeApplication)

//    fun secondScreenComponent(): SecondScreenComponent.Factory

}


@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeOpeningActivity(): OpeningActivity

    @ContributesAndroidInjector
    abstract fun contributeSecondScreenActivity(): SecondScreenActivity
}

@Module
class AppModule {

    @Provides
    @Singleton // NOTE - A SINGLETON HERE MEANS THE O GRAPH/COMPONENT NEEDS TO BE MARKED AS SINGLETON TOO
    fun provideStateSingleton(): com.example.data.StateSingletonImpl {
        return com.example.data.StateSingletonImpl()
    }

//    @Provides
//    fun provideViewModelFactory(): ViewModelFactory {
//        return ViewModelFactory()
//    }

}