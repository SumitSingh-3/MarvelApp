package com.marvelapp.ui.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.marvelapp.R
import com.marvelapp.databinding.FragmentCharacterBinding

class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init
        binding.recyclerView.layoutManager  = GridLayoutManager(activity, 3)

        adapter = CharacterAdapter()
        binding.recyclerView.adapter = adapter
    }

}