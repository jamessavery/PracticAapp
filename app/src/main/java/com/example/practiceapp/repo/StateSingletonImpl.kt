package com.example.practiceapp.repo

class StateSingletonImpl : StateSingleton {

    override var isTriggered: Boolean
        get() = isTriggered
        set(value) {}


}