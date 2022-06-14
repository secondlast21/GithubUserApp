package com.dicoding.githubuserapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.githubuserapp.data.local.entity.UserEntity
import com.dicoding.githubuserapp.data.local.room.UserDao

@Database(entities = [UserEntity::class], version = 2)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserDatabase {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java, "user_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as UserDatabase
        }
    }

}