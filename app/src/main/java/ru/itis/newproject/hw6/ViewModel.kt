package ru.itis.newproject.hw6

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val userId = MutableLiveData<Int?>()

    fun setUserId(id: Int) {
        userId.value = id
    }

    fun clearUserId() {
        userId.value = null
    }
}

