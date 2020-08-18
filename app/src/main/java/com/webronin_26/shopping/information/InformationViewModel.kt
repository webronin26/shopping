package com.webronin_26.shopping.information

import android.view.View
import androidx.lifecycle.*
import com.webronin_26.shopping.Event
import com.webronin_26.shopping.VIEWMODEL_INTERNET_ERROR
import com.webronin_26.shopping.VIEWMODEL_INTERNET_SUCCESS
import com.webronin_26.shopping.data.remote.Result
import com.webronin_26.shopping.data.source.ShoppingRepository
import kotlinx.coroutines.launch

class InformationViewModel : ViewModel(), LifecycleObserver {

    var repository: ShoppingRepository? = null

    val viewModelStatus = MutableLiveData<Event<Int>>()

    val loginButtonVisible = MutableLiveData<Int>()

    val mainViewVisible = MutableLiveData<Int>()

    val userNameTextView = MutableLiveData<String>()

    val userIdTextView = MutableLiveData<Int>()

    fun logout(token: String?) {
        if (!token.isNullOrEmpty()) {
            viewModelScope.launch {
                repository?.logout(token).let { result ->
                    if (result is Result.Success) {
                        viewModelStatus.value = Event(VIEWMODEL_INTERNET_SUCCESS)
                    } else {
                        viewModelStatus.value = Event(VIEWMODEL_INTERNET_ERROR)
                    }
                }
            }
        }
    }

    fun statusNotLogin() {
        loginButtonVisible.value = View.VISIBLE
        mainViewVisible.value = View.GONE
        userIdTextView.value = 0
        userNameTextView.value = ""
    }

    fun statusAlreadyLogin(id: Int?, name: String?) {
        loginButtonVisible.value = View.GONE
        mainViewVisible.value = View.VISIBLE
        userIdTextView.value = id
        userNameTextView.value = name
    }
}