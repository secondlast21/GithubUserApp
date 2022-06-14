package com.dicoding.githubuserapp.ui.activity

import android.app.SearchManager
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubuserapp.viewmodel.MainViewModel
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.adapter.Adapter
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listGithub: RecyclerView

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.listUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listGithub = findViewById(R.id.recycler_github)
        listGithub.setHasFixedSize(true)

        mainViewModel.listReview.observe(this) {
            setUserData(it)
            Log.d("apiUser", it.toString())
        }

        mainViewModel.isLoading.observe(this) { loadingState ->
            setState(loadingState)
        }

        setRotation()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.themes -> {
                val toSetting = Intent(this, SettingActivity::class.java)
                startActivity(toSetting)
                true
            }
            R.id.favorites -> {
                val toFavorites = Intent(this, FavoriteActivity::class.java)
                startActivity(toFavorites)
                true
            }
            else -> true
        }
    }

    private fun setUserData(viewUser : List<ItemsItem>) {
        val adapter = Adapter(viewUser)
        binding.recyclerGithub.adapter = adapter

        adapter.setOnItemClickCallback(object : Adapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                showDetailUser(data)
            }
        })
    }

    private fun setRotation() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            listGithub.layoutManager = GridLayoutManager(this, 2)
        } else {
            listGithub.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun showDetailUser(data: ItemsItem) {
        val toDetail = Intent(this@MainActivity, DetailActivity::class.java)
        toDetail.putExtra(DetailActivity.ITEM_USER, data.login)
        startActivity(toDetail)
    }

    private fun setState(isLoading: Boolean) {
        binding.progressBar4.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        binding.recyclerGithub.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }
}