package com.sunplacestudio.movieapplication.core

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel<State, Event>(application: Application) : AndroidViewModel(application) {

    private val TAG = BaseViewModel::class.java.simpleName
    private val _viewStates: MutableLiveData<State?> = MutableLiveData(null)

    protected val scopeIO = CoroutineScope(Dispatchers.IO)

    fun viewStates(): LiveData<State?> = _viewStates

    fun isInitViewState(): Boolean {
        return _viewStates.value != null
    }

    protected var viewState: State
        get() = _viewStates.value
            ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            if (_viewStates.value == value) {
                _viewStates.postValue(null)
            }

            _viewStates.postValue(value)
        }

    abstract fun obtainEvent(viewEvent: Event)

    @SuppressLint("StaticFieldLeak")
    val context: Context = application.applicationContext
}