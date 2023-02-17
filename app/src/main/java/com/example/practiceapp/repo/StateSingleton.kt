package com.example.practiceapp.repo

import kotlinx.coroutines.flow.Flow

interface StateSingleton {

    var isTriggered: Flow<Boolean>

}