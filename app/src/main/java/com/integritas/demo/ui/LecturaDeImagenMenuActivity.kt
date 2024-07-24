package com.integritas.demo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.integritas.demo.databinding.ActivityLecturaDeImagenMenuBinding

class LecturaDeImagenMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLecturaDeImagenMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLecturaDeImagenMenuBinding.inflate(layoutInflater)
        agregarListeners(binding)
        setContentView(binding.root)
    }

    private fun agregarListeners(binding : ActivityLecturaDeImagenMenuBinding) {

    }
}

