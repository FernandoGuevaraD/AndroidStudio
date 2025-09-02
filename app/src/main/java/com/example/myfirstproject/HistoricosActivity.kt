package com.example.myfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView

class HistoricosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historicos)

        val listView = findViewById<ListView>(R.id.listViewHistoricos)
        val botonVolver = findViewById<ImageButton>(R.id.botonVolver)

        val dbHelper = IMCDatabaseHelper(this)
        val registros = dbHelper.obtenerRegistros()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, registros)
        listView.adapter = adapter

        botonVolver.setOnClickListener {
            finish() // Cierra y vuelve a MainActivity
        }
    }
}
