package com.marvelapp.ui.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp.databinding.AdapterGridBinding


class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = AdapterGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 30
    }

    inner class CharacterViewHolder(binding: AdapterGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}

