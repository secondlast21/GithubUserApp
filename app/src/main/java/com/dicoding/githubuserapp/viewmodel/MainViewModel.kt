package com.dicoding.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.data.remote.response.UserResponse
import com.dicoding.githubuserapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _listReview = MutableLiveData<List<ItemsItem>>()
    val listReview: LiveData<List<ItemsItem>> = _listReview
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        listUser()
        _isLoading.value = false
    }

    fun listUser(USERNAME: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(USERNAME)

        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listReview.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}