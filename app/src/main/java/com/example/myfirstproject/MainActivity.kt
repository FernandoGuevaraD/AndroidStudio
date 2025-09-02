package com.example.myfirstproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var dbHelper: IMCDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pesoEditText = findViewById<EditText>(R.id.peso)
        val estaturaEditText = findViewById<EditText>(R.id.estatura)
        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView3)
        val botonHistorico = findViewById<ImageButton>(R.id.botonHistoricos)
        val botonUsuario = findViewById<ImageButton>(R.id.botonusuario)

        // Inicializar BD
        dbHelper = IMCDatabaseHelper(this)

        button.setOnClickListener {
            val peso = pesoEditText.text.toString().toFloatOrNull()
            val estatura = estaturaEditText.text.toString().toFloatOrNull()

            if (peso != null && estatura != null && estatura > 0) {
                val imc = peso / (estatura * estatura)
                val mensaje = when {
                    imc < 18.5 -> "Bajo peso"
                    imc < 25 -> "Peso normal"
                    imc < 30 -> "Sobrepeso"
                    else -> "Obesidad"
                }

                textView.text = "Tu IMC es %.2f: %s".format(imc, mensaje)

                // Guardar en BD
                val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                val usuario = dbHelper.obtenerUsuario() ?: "Desconocido"

                dbHelper.insertarRegistro(fecha, hora, imc, mensaje, usuario)

                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Por favor ingresa peso y estatura válidos", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón para abrir históricos
        botonHistorico.setOnClickListener {
            val intent = Intent(this, HistoricosActivity::class.java)
            startActivity(intent)
        }
        botonUsuario.setOnClickListener {
            val intent = Intent(this, RegistrarUsuarioActivity::class.java)
            startActivity(intent)
        }
    }
}
