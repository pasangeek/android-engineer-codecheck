package jp.co.yumemi.android.code_check.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.databinding.LayoutItemBinding
import jp.co.yumemi.android.code_check.ui.search.diff_util

import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData

/**
 * Interface definition for the click listener of items in the adapter.
 */
class GithubRepositoryDetailAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<GithubRepositoryData, GithubRepositoryDetailAdapter.ViewHolder>(diff_util) {

    /**
     * Called when an item in the adapter is clicked.
     *
     * @param item The clicked GithubRepositoryData item.
     */
    interface OnItemClickListener {
        fun itemClick(item: GithubRepositoryData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val gitHubRepositoryItem = getItem(position)

            holder.binding.repositoryNameView.text = gitHubRepositoryItem.name
        } catch (e: Exception) {
            Log.e("GithubRepositoryDetailAdapter", "Error in onBindViewHolder: ${e.message}")
        }
    }

    inner class ViewHolder(val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
        /**
         * ViewHolder class for the adapter. Represents an item view in the RecyclerView.
         *
         * @param binding The ViewBinding object for the item layout.
         */
        init {
            try {
                itemView.setOnClickListener {
                    itemClickListener.itemClick(getItem(absoluteAdapterPosition))
                }
            } catch (e: Exception) {
                Log.e("GithubRepositoryDetailAdapter", "Error in click listener: ${e.message}")
            }
        }
    }
}