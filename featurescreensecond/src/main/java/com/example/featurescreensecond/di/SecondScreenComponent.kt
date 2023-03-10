package com.example.featurescreensecond.di

import com.example.featurescreensecond.secondscreen.SecondScreenActivity
import dagger.Subcomponent

@Subcomponent
interface SecondScreenComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SecondScreenComponent
    }

    fun inject(activity: SecondScreenActivity)

}