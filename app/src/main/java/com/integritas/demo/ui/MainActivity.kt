package com.integritas.demo.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.integritas.demo.ui.theme.DemoInvestigacionTheme

//Id de cliente web: 57994672130-gp8gj8i2n2gs0raakqs7jr3okmng28u8.apps.googleusercontent.com
//Id de cliente: 57994672130-ciut5u2vd2jvd06e0njd3pm3hr5502pj.apps.googleusercontent.com
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoInvestigacionTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    GoogleSignInButton()
                }
            }
        }
    }
}

@Composable
fun GoogleSignInButton() {
    val onClick : () -> Unit = {

    }
    Button(onClick = onClick) {
        Text(text = "Sign in with Google")
    }
}

@Preview(showBackground = true)
@Composable
fun GoogleSignInButtonPreview() {
    DemoInvestigacionTheme {
        GoogleSignInButton()
    }
}