package com.example.practiceapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class OpeningViewModel : ViewModel() {

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event: SharedFlow<Event> = _event

    fun onBottomSheetButtonClicked() {
        viewModelScope.launch {
            _event.emit(Event.OpenBottomSheet)
        }
    }

    sealed class Event {
        object OpenBottomSheet : Event()
    }

}