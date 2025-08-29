package com.example.myfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pesoEditText = findViewById<EditText>(R.id.peso)       // ID del campo de peso
        val estaturaEditText = findViewById<EditText>(R.id.estatura)     // ID del campo de estatura
        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView3)

        button.setOnClickListener {
            val pesoTexto = pesoEditText.text.toString()
            val estaturaTexto = estaturaEditText.text.toString()

            val peso = pesoTexto.toFloatOrNull()
            val estatura = estaturaTexto.toFloatOrNull()

            if (peso != null && estatura != null && estatura > 0) {
                val imc = peso / (estatura * estatura)
                val mensaje = when {
                    imc < 18.5 -> "Bajo pesoo"
                    imc < 25 -> "Peso normal"
                    imc < 30 -> "Sobrepeso"
                    else -> "Obesidad"
                }

                textView.text = "Tu IMC es %.2f: %s".format(imc, mensaje)
            } else {
                Toast.makeText(this, "Por favor ingresa peso y estatura válidos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
