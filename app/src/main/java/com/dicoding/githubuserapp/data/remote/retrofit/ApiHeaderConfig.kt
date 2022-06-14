package com.dicoding.githubuserapp.data.remote.retrofit

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiHeaderConfig(private val ACCESS_TOKEN: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "token $ACCESS_TOKEN")
            .build()
        return chain.proceed(request)
    }
}