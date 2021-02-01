package com.pratik.github.ui.commits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pratik.github.data.remote.dto.Root
import com.pratik.github.databinding.CommitListItemBinding

class CommitListAdapter :
    PagedListAdapter<Root, CommitListAdapter.ViewHolder>(CommitSettDiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CommitListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val root = getItem(position)
        root?.let {
            holder.apply {
                bindView(root)
            }
        }
    }



    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    class ViewHolder(private val binding: CommitListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(root: Root) {
            binding.commitAuthorName.text = root.commit.author.name
            binding.commitHash.text = root.sha
            binding.commitMessage.text = root.commit.message
        }
    }

}

private class CommitSettDiffCallback : DiffUtil.ItemCallback<Root>() {

    override fun areItemsTheSame(oldItem: Root, newItem: Root): Boolean {
        return oldItem.node_id == newItem.node_id
    }

    override fun areContentsTheSame(oldItem: Root, newItem: Root): Boolean {
        return oldItem == newItem
    }
}