package com.dicoding.githubuserapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class UserEntity (
    @field:ColumnInfo(name = "uName")
    @field:PrimaryKey
    val uName: String,

    @field:ColumnInfo(name = "avatarUrl")
    val avatarUrl: String,
): Parcelable
