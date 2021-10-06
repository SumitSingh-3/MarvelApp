package com.marvelapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelLazy
import androidx.navigation.findNavController
import com.marvelapp.R
import com.marvelapp.databinding.ActivityMainBinding
import com.marvelapp.viewmodal.MarvelViewModal

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MarvelViewModal by ViewModelLazy(
        MarvelViewModal::class,
        { viewModelStore },
        { defaultViewModelProviderFactory }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation(){
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.character->findNavController(binding.frameContainerView.id).navigate(R.id.frg_character)
                R.id.comic->findNavController(binding.frameContainerView.id).navigate(R.id.frg_comic)
            }
            true
        }
    }

}