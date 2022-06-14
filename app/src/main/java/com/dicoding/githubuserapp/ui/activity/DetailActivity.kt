package com.dicoding.githubuserapp.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.viewmodel.DetailViewModel
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.adapter.SectionsPagerAdapter
import com.dicoding.githubuserapp.data.local.entity.UserEntity
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.databinding.ActivityDetailBinding
import com.dicoding.githubuserapp.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private var entityGithub : UserEntity? = null

    companion object {
        const val ITEM_USER = "item_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
    private lateinit var binding: ActivityDetailBinding

    val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private fun setData(user: ItemsItem) {
        Glide.with(this)
            .load(user.avatarUrl)
            .circleCrop()
            .into(binding.detailActivity.detailAvatar)

        binding.detailActivity.apply {
            detailUsername.text = user.login
            detailName.text = user.name
            detailRepository.text = user.publicRepos.toString()
            detailFollowers.text = user.followers.toString()
            detailFollowing.text = user.following.toString()
            detailCompany.text = user.company.toString()
            detailLocation.text = user.location.toString()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uname = intent.getStringExtra(ITEM_USER)
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        Log.d("test", uname.toString())

        if (uname != null) {
            detailViewModel.infoDetailUser(uname).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is com.dicoding.githubuserapp.data.Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is com.dicoding.githubuserapp.data.Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            setData(result.data)
                            Log.d("resultData", result.data.toString())
                            entityGithub = UserEntity(
                                result.data.login.toString(),
                                result.data.avatarUrl.toString()
                            )
                        }

                        is com.dicoding.githubuserapp.data.Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@DetailActivity, "Terjadi kesalahan" + result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            detailViewModel.isFavorited(uname).observe(this) { favoritedUser ->
                val addBtn = binding.addBtn
                if(favoritedUser) {
                    addBtn.setOnClickListener {
                        detailViewModel.deleteUser(entityGithub!!)
                    }
                    addBtn.setImageDrawable(
                        ContextCompat.getDrawable(addBtn.context, R.drawable.ic_baseline_favorite_24)
                    )
                } else {
                    addBtn.setOnClickListener {
                        detailViewModel.insertUser(entityGithub!!)
                    }
                    addBtn.setImageDrawable(
                        ContextCompat.getDrawable(addBtn.context, R.drawable.ic_baseline_favorite_border_24)
                    )
                }
            }
            sectionsPagerAdapter.unameAdapter = uname
        }

//        detailViewModel.listDetail.observe(this) {
//            dataGithub = it
//            setData()
//        }
//
//        detailViewModel.isLoading.observe(this) { loadingState ->
//            setState(loadingState)
//        }

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        binding.detailActivity.root.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }
}