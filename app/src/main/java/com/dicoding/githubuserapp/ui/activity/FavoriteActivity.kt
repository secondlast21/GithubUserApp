
package com.dicoding.githubuserapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.adapter.Adapter
import com.dicoding.githubuserapp.data.local.entity.UserEntity
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.databinding.ActivityFavoriteBinding
import com.dicoding.githubuserapp.viewmodel.DetailViewModel
import com.dicoding.githubuserapp.viewmodel.FavoriteViewModel
import com.dicoding.githubuserapp.viewmodel.FavoriteViewModelFactory
import com.dicoding.githubuserapp.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)

        favoriteViewModel.getFavoritedUser().observe(this@FavoriteActivity) { favoritedUser ->
            val listFavorite: List<UserEntity> = favoritedUser
            val listUser = ArrayList<ItemsItem>()
            for (favUser in listFavorite) {
                val user = ItemsItem (
                    favUser.uName,
                    favUser.avatarUrl,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                )
                listUser.add(user)
            }
            Log.d("listUser", listFavorite.toString())

            val userAdapter = Adapter(listUser)

            binding.recyclerFavorite.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = userAdapter
            }

            userAdapter.setOnItemClickCallback(object: Adapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemsItem) {
                    val toDetail = Intent(this@FavoriteActivity, DetailActivity::class.java)
                    toDetail.putExtra(DetailActivity.ITEM_USER, data.login)
                    startActivity(toDetail)
                }
            })
        }
        setContentView(binding.root)
    }

}