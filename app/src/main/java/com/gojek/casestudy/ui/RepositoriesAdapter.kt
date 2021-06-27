package com.gojek.casestudy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gojek.casestudy.R
import com.gojek.casestudy.model.GitHubRepository
import com.gojek.casestudy.util.ImageLoader
import kotlinx.android.synthetic.main.repository_list_item.view.*

class RepositoriesAdapter(private val gitHubRepositories: List<GitHubRepository>): RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {

    private var itemSelected = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.repository_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_name.text = gitHubRepositories[position].name
        holder.itemView.tv_description.text = gitHubRepositories[position].description
        holder.itemView.tv_html.text = gitHubRepositories[position].owner.htmlUrl
        holder.itemView.tv_language.text = gitHubRepositories[position].language
        holder.itemView.tv_watcher.text = gitHubRepositories[position].watchersCount.toString()
        holder.itemView.tv_fork.text = gitHubRepositories[position].forksCount.toString()

        ImageLoader.loadImage(holder.itemView.iv_avatar, gitHubRepositories[position].owner.avatarUrl)

        if (gitHubRepositories[position].itemSelected)
            holder.itemView.layer_expanded.visibility = View.VISIBLE
        else
            holder.itemView.layer_expanded.visibility = View.GONE

        holder.itemView.setOnClickListener {
            //Same item clicked again
            if (itemSelected == position) {
                gitHubRepositories[itemSelected].itemSelected = false
                notifyItemChanged(itemSelected)
                itemSelected = -1
            } else {
                //Reset previously selected item
                if (itemSelected >= 0) {
                    gitHubRepositories[itemSelected].itemSelected = false
                    notifyItemChanged(itemSelected)
                }

                //Select  new item
                itemSelected = position
                gitHubRepositories[position].itemSelected = true
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return gitHubRepositories.count()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}