package com.marvelapp.ui.comic.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp.databinding.AdapterGridBinding

class ComicAdapter: RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val binding = AdapterGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 30
    }

    inner class ComicViewHolder(binding: AdapterGridBinding):
        RecyclerView.ViewHolder(binding.root) {

    }
}