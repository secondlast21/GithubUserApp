package com.dicoding.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.UserRepository
import com.dicoding.githubuserapp.data.local.entity.UserEntity
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    fun infoDetailUser(USERNAME: String) = userRepository.infoDetailUser(USERNAME)
    fun insertUser(USER: UserEntity) = userRepository.insertUser(USER)
    fun deleteUser(USER: UserEntity) = userRepository.deleteUser(USER)
    fun isFavorited(USERNAME: String) = userRepository.isFavorited(USERNAME)

//    private val _listReview = MutableLiveData<ItemsItem>()
//    val listDetail: LiveData<ItemsItem> = _listReview
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    companion object{
//        private const val TAG = "DetailViewModel"
//    }
//
//    fun infoDetailUser(USERNAME: String = "") {
//        _isLoading.value = true
//
//        val clientDetail = ApiConfig.getApiService().detailUser(USERNAME)
//
//
//        clientDetail.enqueue(object : Callback<ItemsItem> {
//            override fun onResponse(
//                call: Call<ItemsItem>,
//                response: Response<ItemsItem>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    _listReview.value = response.body()
//                    Log.d("detailViewModel", response.body()?.login!!)
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//            override fun onFailure(call: Call<ItemsItem>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//        })
//    }
}