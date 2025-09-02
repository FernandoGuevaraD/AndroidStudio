package com.example.myfirstproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegistrarUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuario)

        val editNombre = findViewById<EditText>(R.id.editNombre)
        val botonGuardar = findViewById<Button>(R.id.botonGuardar)
        val botonVolver = findViewById<Button>(R.id.botonVolver)

        val dbHelper = IMCDatabaseHelper(this)

        botonGuardar.setOnClickListener {
            val nombre = editNombre.text.toString().trim()
            if (nombre.isNotEmpty()) {
                dbHelper.guardarUsuario(nombre)
                Toast.makeText(this, "Usuario guardado", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_SHORT).show()
            }
        }

        botonVolver.setOnClickListener {
            finish() // Regresar a MainActivity si ya existe un usuario
        }
    }
}
