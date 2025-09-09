package com.example.myfirstproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        title = "Autenticación"
        val btRegistrar = findViewById<Button>(R.id.botonGuardar)
        val btLogin = findViewById<Button>(R.id.botoningresar)
        val email = findViewById<EditText>(R.id.correo)
        val password = findViewById<EditText>(R.id.contra)
        val intent = Intent(this, MainActivity::class.java)

        btRegistrar.setOnClickListener {
            if (email.text.isNotEmpty() && password.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(intent)
                        } else {
                            showAlert()
                        }
                    }
            }
        }

        btLogin.setOnClickListener {
            if (email.text.isNotEmpty() && password.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(intent)
                        } else {
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
