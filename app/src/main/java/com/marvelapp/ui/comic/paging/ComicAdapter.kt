package com.marvelapp.ui.comic.paging

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp.databinding.AdapterGridBinding
import com.marvelapp.modals.common.PagingItem
import com.marvelapp.utils.AppUtil

class ComicAdapter: PagedListAdapter<PagingItem, ComicAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<PagingItem> =
            object : DiffUtil.ItemCallback<PagingItem>() {
                override fun areItemsTheSame(oldItem: PagingItem, newItem: PagingItem): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: PagingItem, newItem: PagingItem): Boolean {
                    return oldItem === newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: AdapterGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(char: PagingItem) {
            binding.name.text = char.title
            AppUtil.loadImage(
                binding.image,
                "${char.thumbnail.path}.${char.thumbnail.extension}"
            )
        }
    }

}