package com.dicoding.githubuserapp.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.UserRepository

class FavoriteViewModel (
    private val userRepository: UserRepository
        ) : ViewModel() {
    fun getFavoritedUser() = userRepository.getFavoritedUser()
}