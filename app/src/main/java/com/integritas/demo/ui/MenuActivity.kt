package com.integritas.demo.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.integritas.demo.ui.theme.DemoInvestigacionTheme

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoInvestigacionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MostrarMenu()
                }
            }
        }
    }
}

@Composable
fun MostrarMenu() {
    val context = LocalContext.current
    val onClickLecturaDeImagen : () -> Unit = {
        val intent = Intent(context, LecturaDeImagenMenuActivity::class.java)
        context.startActivity(intent)
    }

    val onClickReconocimientoFacial : () -> Unit = {
        Toast.makeText(context, "Reconocimiento facial proximamente", Toast.LENGTH_SHORT).show()
    }

    Column( verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = onClickLecturaDeImagen) {
            Text(text = "Ir a la lectura de imagen")
        }
        Button(onClick = onClickReconocimientoFacial) {
            Text(text = "Reconocimiento facial")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MostrarMenuPreview() {
    DemoInvestigacionTheme {
        MostrarMenu()
    }
}
