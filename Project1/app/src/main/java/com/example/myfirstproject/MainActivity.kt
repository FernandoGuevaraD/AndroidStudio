package com.example.myfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editTextText)
        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView3)

        button.setOnClickListener {
            val nombre = editText.text.toString()
            if (nombre.isNotEmpty()) {
                textView.text = nombre
            } else {
                textView.text = "Por favor escribe un nombre"
            }
        }
    }
}
