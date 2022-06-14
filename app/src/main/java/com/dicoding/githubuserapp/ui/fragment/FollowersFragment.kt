package com.dicoding.githubuserapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.viewmodel.FollowersViewModel
import com.dicoding.githubuserapp.adapter.Adapter
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.databinding.FragmentFollowersBinding
import com.dicoding.githubuserapp.ui.activity.DetailActivity

class FollowersFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding
    private val followersViewModel by viewModels<FollowersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = arguments?.getString(username)
        if (name != null) {
            followersViewModel.infoFollowersUser(name)
            Log.d("usernamefollowers", name)
        }

        followersViewModel.listFollowers.observe(requireActivity()) {
            setUserData(it)
            Log.d("logcatFollowers", it.toString())
        }

        followersViewModel.isLoading.observe(requireActivity()) { loadingState ->
            setState(loadingState)
        }

        setRotation()
    }

    private fun setUserData(viewUser : List<ItemsItem>) {
        val adapter = Adapter(viewUser)
        binding.recyclerFollowers.adapter = adapter

        adapter.setOnItemClickCallback(object : Adapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                showDetailUser(data)
            }
        })
    }

    private fun setRotation() {
        binding.recyclerFollowers.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showDetailUser(data: ItemsItem) {
        val toDetail = Intent(requireActivity(), DetailActivity::class.java)
        toDetail.putExtra(DetailActivity.ITEM_USER, data.login)
        startActivity(toDetail)
    }

    private fun setState(isLoading: Boolean) {
        binding.progressBar3.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        binding.recyclerFollowers.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }

    companion object {
        const val username = "username"
    }
}