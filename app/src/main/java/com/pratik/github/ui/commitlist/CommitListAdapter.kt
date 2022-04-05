package com.pratik.github.ui.commitlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pratik.github.data.remote.dto.Root
import com.pratik.github.databinding.CommitListItemBinding

class CommitListAdapter(private val itemClickListener: OnItemClickListener) :
    PagedListAdapter<Root, CommitListAdapter.ViewHolder>(CommitSettDiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CommitListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val root = getItem(position)
        root?.let {
            holder.apply {
                bindView(root, itemClickListener)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    class ViewHolder(private val binding: CommitListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(root: Root, clickListener: OnItemClickListener) {
            binding.commitAuthorName.text = root.commit?.author?.name
            binding.commitHash.text = root.sha
            binding.commitMessage.text = root.commit?.message
            itemView.setOnClickListener {
                clickListener.onItemClicked(root = root)
            }
        }
    }
}

interface OnItemClickListener {
    fun onItemClicked(root: Root)
}

private class CommitSettDiffCallback : DiffUtil.ItemCallback<Root>() {

    override fun areItemsTheSame(oldItem: Root, newItem: Root): Boolean {
        return oldItem.node_id == newItem.node_id
    }

    override fun areContentsTheSame(oldItem: Root, newItem: Root): Boolean {
        return oldItem == newItem
    }
}
