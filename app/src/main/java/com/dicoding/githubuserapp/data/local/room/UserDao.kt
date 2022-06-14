package com.dicoding.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.githubuserapp.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    @Query("SELECT  * FROM user")
    fun getFavoritedUser(): LiveData<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM user WHERE uName = :username)")
    fun isFavorited(username : String) : LiveData<Boolean>
}