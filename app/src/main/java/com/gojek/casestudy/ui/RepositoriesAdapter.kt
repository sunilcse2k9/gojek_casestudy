package com.gojek.casestudy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gojek.casestudy.model.Repository
import com.gojek.casestudy.util.ImageLoader
import com.target.casestudy.R
import kotlinx.android.synthetic.main.repository_list_item.view.*

class RepositoriesAdapter(val repositories: List<Repository>): RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.repository_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_name.text = repositories[position].name
        holder.itemView.tv_description.text = repositories[position].description
        holder.itemView.tv_html.text = repositories[position].owner.htmlUrl
        holder.itemView.tv_language.text = repositories[position].language
        holder.itemView.tv_watcher.text = repositories[position].watchersCount.toString()
        holder.itemView.tv_fork.text = repositories[position].forksCount.toString()

        ImageLoader.loadImage(holder.itemView.iv_avatar, repositories[position].owner.avatarUrl)
    }

    override fun getItemCount(): Int {
        return repositories.count()
    }
}