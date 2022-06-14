package com.dicoding.githubuserapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.githubuserapp.data.local.entity.UserEntity
import com.dicoding.githubuserapp.data.local.room.UserDao
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.data.remote.retrofit.ApiService
import com.dicoding.githubuserapp.utils.AppExecutors
import com.dicoding.githubuserapp.viewmodel.DetailViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<Result<ItemsItem>>()

    fun infoDetailUser(USERNAME: String = ""): LiveData<Result<ItemsItem>>{
        result.value = Result.Loading
        val client = apiService.detailUser(USERNAME)
        client.enqueue(object: Callback<ItemsItem> {
            override fun onResponse(
                call: Call<ItemsItem>,
                response: Response<ItemsItem>
            ) {
                if (response.isSuccessful) {
                    result.value = Result.Success(response.body()!!)
                    Log.d("detailViewModel", response.body()?.login!!)
                }
            }
            override fun onFailure(call: Call<ItemsItem>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        return result
    }

    fun getFavoritedUser(): LiveData<List<UserEntity>> {
        return userDao.getFavoritedUser()
    }

    fun insertUser(user: UserEntity) {
        appExecutors.diskIO.execute {
            userDao.insert(user)
        }
    }

    fun deleteUser(user: UserEntity) {
        appExecutors.diskIO.execute {
            userDao.delete(user)
        }
    }

    fun isFavorited(uName: String): LiveData<Boolean> = userDao.isFavorited(uName)

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao,
            appExecutors: AppExecutors
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userDao, appExecutors)
            }.also { instance = it }
    }
}