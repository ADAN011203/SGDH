package com.example.sgdh.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : Any> : ViewModel() {

    protected abstract val _state: MutableStateFlow<State>
    val state: StateFlow<State> get() = _state

    protected fun updateState(update: (State) -> State) {
        viewModelScope.launch {
            _state.value = update(_state.value)
        }
    }

    protected fun setState(newState: State) {
        viewModelScope.launch {
            _state.value = newState
        }
    }
}