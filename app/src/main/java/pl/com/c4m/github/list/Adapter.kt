package pl.com.c4m.github.list

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isGone
import pl.com.c4m.github.NetworkState
import pl.com.c4m.github.R
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
        private val layoutInflater: LayoutInflater,
        private val onItemClick: (RepositoryListing) -> Unit
) : PagedListAdapter<RepositoryListing, RecyclerView.ViewHolder>(REPOSITORY_DIFF_CALLBACK) {

    var networkState: NetworkState? = null
        set(value) {
            val oldValue = field
            val hadExtraRow = hasExtraRow()
            field = value
            val hasExtraRow = hasExtraRow()
            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow) {
                    notifyItemRemoved(super.getItemCount())
                } else {
                    notifyItemInserted(super.getItemCount())
                }
            } else if (hasExtraRow && oldValue != value) {
                notifyItemChanged(itemCount - 1)
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_repository -> RepositoryViewHolder(layoutInflater.inflate(R.layout.item_repository, parent, false), onItemClick)
            R.layout.item_network_state -> NetworkStateViewHolder(layoutInflater.inflate(R.layout.item_network_state, parent, false))
            else -> throw IllegalStateException("Unknown viewType = $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_repository -> {
                with(holder as RepositoryViewHolder) {
                    getItem(position)?.let { bind(it) }
                }
            }
            R.layout.item_network_state -> (holder as NetworkStateViewHolder).bind(networkState)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_repository
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.Success
}

private class RepositoryViewHolder(
        itemView: View,
        private val onItemClick: (RepositoryListing) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    val nameView: TextView = itemView.findViewById(R.id.itemRepositoryNameView)
    val starsView: TextView = itemView.findViewById(R.id.itemRepositoryStarsCountView)

    fun bind(item: RepositoryListing) {
        nameView.text = item.fullName
        starsView.text = itemView.context.resources.getQuantityString(R.plurals.stars_count, item.stargazersCount, item.stargazersCount)
        itemView.setOnClickListener { onItemClick(item) }
    }
}

private class NetworkStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val progressBar: ProgressBar = itemView.findViewById(R.id.itemProgressBar)

    fun bind(networkState: NetworkState?) {
        progressBar.isGone = networkState != NetworkState.Loading
    }
}