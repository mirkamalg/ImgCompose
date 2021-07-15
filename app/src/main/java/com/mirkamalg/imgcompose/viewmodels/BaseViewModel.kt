package com.mirkamalg.imgcompose.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mirkamalg.imgcompose.network.exceptions.BaseNetworkException
import com.mirkamalg.imgcompose.network.exceptions.NoConnectionException
import com.mirkamalg.imgcompose.state.BaseState
import com.mirkamalg.imgcompose.utils.hasConnection
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val mCoroutineContext = Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch(Dispatchers.Main) {
            handleExceptions(throwable)
        }
    }

    private val _baseState = MutableStateFlow(BaseState())
    val baseState: StateFlow<BaseState> = _baseState

    fun <T> makeRequest(
        sentRequest: suspend () -> T,
        onSuccess: suspend (T) -> Unit,
        showLoadingDialog: Boolean = true
    ) {
        viewModelScope.launch(mCoroutineContext) {
            if (hasConnection(getApplication())) {
                if (showLoadingDialog) {
                    _baseState.value = BaseState(isLoading = true)
                }
                val data = sentRequest()
                onSuccess(data)
                _baseState.value = BaseState(isLoading = false)
            } else {
                throw NoConnectionException
            }
        }
    }

    fun clearErrorState() {
        _baseState.value = BaseState(isLoading = false, errorMessage = null)
    }

    private fun handleExceptions(throwable: Throwable?) {
        val exception = throwable as? BaseNetworkException
        if (exception != null) {
            _baseState.value = BaseState(
                isLoading = false,
                errorMessage = exception.message
            )
        } else {
            _baseState.value = BaseState(
                isLoading = false,
                errorMessage = throwable?.message
            )
        }

    }
}