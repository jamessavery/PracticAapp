package com.example.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Singleton

class StateSingletonImpl() : StateSingleton {

    private val _actionLiveData = MutableLiveData<String>()
    val actionLiveData: LiveData<String> = _actionLiveData

    private var singletonVariable: String? = null

    override fun setTriggeredTing(ting: String) {
        singletonVariable = ting
    }

    override fun getTriggeredTing(): String? {
        return singletonVariable
    }

}