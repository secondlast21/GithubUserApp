package com.dicoding.githubuserapp.data.remote.retrofit

import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") string: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun detailUser (
        @Path(value = "username") string: String
    ): Call<ItemsItem>

    @GET("users/{username}/followers")
    fun followersUser (
        @Path(value = "username") string: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun followingUser (
        @Path(value = "username") string: String
    ): Call<List<ItemsItem>>
}