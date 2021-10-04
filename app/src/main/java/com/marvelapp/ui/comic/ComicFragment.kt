package com.marvelapp.ui.comic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.marvelapp.databinding.FragmentComicBinding


class ComicFragment : Fragment() {

    private lateinit var binding: FragmentComicBinding
    private lateinit var adapter: ComicAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentComicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init
        binding.recyclerView.layoutManager  = GridLayoutManager(activity, 3)

        adapter = ComicAdapter()
        binding.recyclerView.adapter = adapter

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = group.findViewById(checkedId)

                Toast.makeText(context, chip?.text, Toast.LENGTH_SHORT).show()

        }
    }

}