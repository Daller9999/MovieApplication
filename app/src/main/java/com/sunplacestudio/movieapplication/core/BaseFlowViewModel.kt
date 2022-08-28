package com.sunplacestudio.movieapplication.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class BaseFlowViewModel<State, Event>(
    initState: State
) : ViewModel() {

    private val TAG = BaseViewModel::class.java.simpleName
    private val _viewStates: MutableStateFlow<State> = MutableStateFlow(initState)

    protected val scopeIO = CoroutineScope(Dispatchers.IO)

    fun viewStates(): StateFlow<State> = _viewStates

    abstract fun obtainEvent(viewEvent: Event)

    protected fun update(update: (State) -> State) {
        _viewStates.update(update)
    }
}