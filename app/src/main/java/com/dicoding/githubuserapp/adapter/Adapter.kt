package com.dicoding.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.R

class Adapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<Adapter.ApiViewHolder>() {
    private lateinit var onItemClickCallback : OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ApiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatar: ImageView = itemView.findViewById(R.id.list_avatar)
        var username: TextView = itemView.findViewById(R.id.list_username)
        var username2: TextView = itemView.findViewById(R.id.list_username2)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : ApiViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.row_user, viewGroup, false)
        return ApiViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ApiViewHolder, position: Int) {
        val (login, avatarUrl) = listUser[position]
        viewHolder.username.text = login
        viewHolder.username2.text = login
        Glide.with(viewHolder.avatar.context)
            .load(avatarUrl)
            .circleCrop()
            .into(viewHolder.avatar)
        viewHolder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(listUser[viewHolder.adapterPosition])}
    }
    override fun getItemCount() = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }
}