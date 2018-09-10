package pl.com.c4m.github.list

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pl.com.c4m.github.api.RepositoryListing

val REPOSITORY_DIFF_CALLBACK = object : DiffUtil.ItemCallback<RepositoryListing>() {
    override fun areItemsTheSame(oldItem: RepositoryListing?, newItem: RepositoryListing?): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: RepositoryListing?, newItem: RepositoryListing?): Boolean {
        return oldItem == newItem
    }

}

class RepositoryAdapter(
        private val layoutInflater: LayoutInflater
) : PagedListAdapter<RepositoryListing, RepositoryViewHolder>(REPOSITORY_DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false))
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }
}

class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameView: TextView = itemView as TextView

    fun bind(item: RepositoryListing) {
        nameView.text = item.fullName
    }
}