package jp.co.yumemi.android.code_check.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.diff_util
import jp.co.yumemi.android.code_check.model.GithubRepositoryData

class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<GithubRepositoryData, CustomAdapter.ViewHolder>(diff_util){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    interface OnItemClickListener{
        fun itemClick(item: GithubRepositoryData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item= getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text=
            item.name

        holder.itemView.setOnClickListener{
            itemClickListener.itemClick(item)
        }
    }
}