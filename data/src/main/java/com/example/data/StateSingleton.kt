package com.example.data

import androidx.lifecycle.MutableLiveData

interface StateSingleton {

    fun setTriggeredTing(ting: String)

    fun getTriggeredTing(): String

}