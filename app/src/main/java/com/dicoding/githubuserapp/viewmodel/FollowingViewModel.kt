package com.dicoding.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing: LiveData<List<ItemsItem>> = _listFollowing
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    init {
        infoFollowingUser()
    }

    fun infoFollowingUser(USERNAME: String = "") {
        _isLoading.value = true

        val clientDetail = ApiConfig.getApiService().followingUser(USERNAME)


        clientDetail.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                    Log.d("detailViewModel", response.body().toString())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}