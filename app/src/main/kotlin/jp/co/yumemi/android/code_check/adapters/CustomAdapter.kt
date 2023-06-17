package jp.co.yumemi.android.code_check.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.databinding.LayoutItemBinding
import jp.co.yumemi.android.code_check.diff_util
import jp.co.yumemi.android.code_check.model.GithubRepositoryData

class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<GithubRepositoryData, CustomAdapter.ViewHolder>(diff_util) {


    interface OnItemClickListener {
        fun itemClick(item: GithubRepositoryData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gitHubRepositoryItem = getItem(position)

        holder.binding.repositoryNameView.text = gitHubRepositoryItem.name
    }

    inner class ViewHolder(val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                itemClickListener.itemClick(getItem(absoluteAdapterPosition))
            }
        }
    }
}