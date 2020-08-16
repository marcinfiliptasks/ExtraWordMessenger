package mf.patomessenger.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus

abstract class BaseViewModel : ViewModel() {

    protected val errorMessageMutable = MutableLiveData<String>()
    val errorMessage: LiveData<String> = errorMessageMutable

    protected val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
        errorMessageMutable.value = throwable.message
    }

    protected val ioContext = viewModelScope + coroutineExceptionHandler
    protected val mainContext = viewModelScope + coroutineExceptionHandler
}