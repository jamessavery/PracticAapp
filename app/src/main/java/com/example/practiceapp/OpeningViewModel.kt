package com.example.practiceapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class OpeningViewModel : ViewModel() {

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event: SharedFlow<Event> = _event

    fun onBottomSheetButtonClicked(toggled: Boolean) {
        viewModelScope.launch {
            if(toggled) {
                _event.emit(Event.CloseBottomSheet)
            } else {
                _event.emit(Event.OpenBottomSheet)
            }
        }
    }

    sealed class Event {
        object OpenBottomSheet : Event()
        object CloseBottomSheet : Event()
    }

}