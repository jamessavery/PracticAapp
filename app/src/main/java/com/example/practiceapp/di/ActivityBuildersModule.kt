package com.example.practiceapp.di

import com.example.featurescreensecond.secondscreen.SecondScreenActivity
import com.example.practiceapp.opening.OpeningActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

// TODO finish notes on below DI approach - https://medium.com/@shashankmohabia/dagger-android-with-mvvm-dependency-injection-for-android-3a7e33ad1013
@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeOpeningActivity(): OpeningActivity

    @ContributesAndroidInjector
    abstract fun contributeSecondScreenActivity(): SecondScreenActivity

}