package com.cleanmvvm.starwars.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleanmvvm.starwars.data.base.BaseException
import com.cleanmvvm.starwars.data.base.UnExpectedException
import kotlinx.coroutines.ExperimentalCoroutinesApi

abstract class BaseViewModel<T> : ViewModel() {

    abstract fun defaultState(): T

    protected val _viewState = MutableLiveData<T>().apply {
        value = defaultState()
    }

    protected val _navState = SingleLiveData<NavEvent>()
    protected val _errorState = SingleLiveData<BaseException>()

    val viewState: LiveData<T> = _viewState
    val navEventsState: LiveData<NavEvent> = _navState
    val errorState:LiveData<BaseException> = _errorState

    protected fun newState(stateCopy: (T) -> T) {
        val oldState = _viewState.value!!
        _viewState.value = stateCopy(oldState)
    }

    protected fun navEvent(navEvent: NavEvent){
        _navState.value = navEvent
    }
}

@ExperimentalCoroutinesApi
suspend fun <Result> result(
    action: suspend () -> Result,
    onError: (BaseException) -> Any = {},
    onSuccess: (Result) -> Any,
    preAction: () -> Unit = {}) {
    try {
        preAction()
        onSuccess(resultOrBaseException { action() })
    } catch (e: BaseException) {
        onError(e)
    }
}

@ExperimentalCoroutinesApi
suspend fun <Result> resultOrBaseException(action: suspend () -> Result): Result {
    return try {
        action()
    } catch (e: Exception) {
        throw returnBaseException(e)
    }
}

fun returnBaseException(e: Exception): BaseException {
    return when (e) {
        is BaseException -> e
        else -> UnExpectedException(e)
    }
}

sealed class State {
    object Success : State()
    object Loading : State()
}

open class NavEvent

class SingleLiveData<T> : MutableLiveData<T>()