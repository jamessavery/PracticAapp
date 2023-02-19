package com.example.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Singleton

@Singleton
class StateSingletonImpl : StateSingleton {

    private val _actionLiveData = MutableLiveData<String>()
    val actionLiveData: LiveData<String> = _actionLiveData

    override fun setTriggeredTing(ting: String) {
        _actionLiveData.postValue(ting)
    }

    override fun getTriggeredTing(): MutableLiveData<String> {
        return _actionLiveData
    }

}