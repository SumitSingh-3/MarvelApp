package com.marvelapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.marvelapp.R
import com.marvelapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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