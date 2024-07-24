package com.integritas.demo.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.integritas.demo.databinding.ActivityLecturaDeImagenMenuBinding

class LecturaDeImagenMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLecturaDeImagenMenuBinding
    private var uri: Uri? = null
    private val galeriaARL : ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                uri = data?.data
                binding.ivImagen.setImageURI(uri)
            } else {
                Toast.makeText(this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show()
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLecturaDeImagenMenuBinding.inflate(layoutInflater)
        agregarListeners(binding)
        setContentView(binding.root)
    }

    private fun agregarListeners(binding : ActivityLecturaDeImagenMenuBinding) {
        binding.btnAbrirGaleria.setOnClickListener {
            abrirGaleria()
        }
        binding.btnAbrirCamara.setOnClickListener {

        }
        binding.btnReconocerTxt.setOnClickListener {

        }
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        galeriaARL.launch(intent)
    }


}

