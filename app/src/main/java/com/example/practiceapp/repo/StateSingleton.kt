package com.example.practiceapp.repo

import androidx.lifecycle.MutableLiveData

interface StateSingleton {

    fun setTriggeredTing(ting: String)

    fun getTriggeredTing(): MutableLiveData<String>

}