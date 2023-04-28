package com.example.practiceapp.di

import com.example.featurescreensecond.secondscreen.SecondScreenActivity
import com.example.featurescreensecond.secondscreen.pageradapter.ColoredFragment
import com.example.practiceapp.opening.OpeningActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

// Resp for letting O graph know of Activities which'll request dependencies; so handles injections for it
@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector // Lets dagger know that given Act is a potential client, so there is no need for any other injection code in that act ((Client as in it'll receive dependencies!))
    abstract fun contributeOpeningActivity(): OpeningActivity

    @ContributesAndroidInjector
    abstract fun contributeSecondScreenActivity(): SecondScreenActivity

    // TODO CReate seperate for Frags
    @ContributesAndroidInjector
    abstract fun contributeColoredFragment(): ColoredFragment

}