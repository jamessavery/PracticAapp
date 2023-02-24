package com.example.practiceapp.di

import com.example.featurescreensecond.SecondScreenActivity
import com.example.practiceapp.opening.OpeningActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeOpeningActivity(): OpeningActivity

    @ContributesAndroidInjector
    abstract fun contributeSecondScreenActivity(): SecondScreenActivity

}