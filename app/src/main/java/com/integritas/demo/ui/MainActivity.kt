package com.integritas.demo.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.integritas.demo.ui.theme.DemoInvestigacionTheme
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

//Id de cliente web: 57994672130-gp8gj8i2n2gs0raakqs7jr3okmng28u8.apps.googleusercontent.com
//Id de cliente: 57994672130-ciut5u2vd2jvd06e0njd3pm3hr5502pj.apps.googleusercontent.com
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoInvestigacionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column( verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        GoogleSignInButton()
                    }
                }
            }
        }
    }
}

@Composable
fun GoogleSignInButton() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val onClick: () -> Unit = {
        val credentialManager = CredentialManager.create(context)
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("57994672130-gp8gj8i2n2gs0raakqs7jr3okmng28u8.apps.googleusercontent.com")
            .setNonce(hashedNonce)
            .build()
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken
                Log.d("Dev_Demo", "GoogleSignInButton: $googleIdToken")
                Toast.makeText(context, "Log in exitoso!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("Dev_Demo", "GoogleSignInButton: ${e.message}")
                Toast.makeText(context, "Mensaje de error: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        }
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