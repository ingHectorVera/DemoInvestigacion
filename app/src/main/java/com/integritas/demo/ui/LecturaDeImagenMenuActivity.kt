package com.integritas.demo.ui

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.integritas.demo.databinding.ActivityLecturaDeImagenMenuBinding

class LecturaDeImagenMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLecturaDeImagenMenuBinding
    private lateinit var textRecognizer: TextRecognizer
    private var uri: Uri? = null
    private val galeriaARL : ActivityResultLauncher<Intent> = obtenerGaleriaARL()
    private val camaraARL : ActivityResultLauncher<Intent> = obtenerCamaraARL()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLecturaDeImagenMenuBinding.inflate(layoutInflater)
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        binding.pbProgresoDeReconocimiento.visibility = View.GONE
        agregarListeners(binding)
        setContentView(binding.root)
    }

    private fun agregarListeners(binding : ActivityLecturaDeImagenMenuBinding) {
        binding.btnAbrirGaleria.setOnClickListener {
            abrirGaleria()
        }
        binding.btnAbrirCamara.setOnClickListener {
            abrirCamara()
        }
        binding.btnReconocerTxt.setOnClickListener {
            reconocerTexto()
        }
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        galeriaARL.launch(intent)
    }

    private fun abrirCamara() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Titulo")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripcion")

        uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        camaraARL.launch(intent)
    }

    private fun reconocerTexto() {
        if(uri == null) {
            Toast.makeText(this, "Seleccionar o tomar foto del texto", Toast.LENGTH_SHORT).show()
        } else {
            reconocerTextoDeImagen()
        }
    }

    private fun reconocerTextoDeImagen() {

        binding.pbProgresoDeReconocimiento.visibility = View.VISIBLE
        try {
            val inputImage = uri?.let { InputImage.fromFilePath(this, it) }
            val textTask : Task<Text>? = inputImage?.let {
                textRecognizer.process(it)
                    .addOnSuccessListener { text ->
                        val text = text.text
                        binding.tvTextoReconocido.text = text
                        binding.pbProgresoDeReconocimiento.visibility = View.GONE
                    }
                    .addOnFailureListener { e ->
                        Log.e("DemoInvestigacion", "reconocerTextoDeImagen: ${e.message}", )
                        binding.pbProgresoDeReconocimiento.visibility = View.GONE
                    }
            }
        } catch (e:Exception) {
            Log.e("DemoInvestigacion", "reconocerTextoDeImagen: ${e.message}", )
        }

    }

    private fun obtenerGaleriaARL() : ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data
                    uri = data?.data
                    binding.ivImagen.setImageURI(uri)
                    binding.tvTextoReconocido.text = ""
                } else {
                    Toast.makeText(this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun obtenerCamaraARL() : ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    binding.ivImagen.setImageURI(uri)
                    binding.tvTextoReconocido.text = ""
                } else {
                    Toast.makeText(this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}

