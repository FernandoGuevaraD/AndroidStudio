package com.example.myfirstproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class IMCDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "imc.db"
        private const val DATABASE_VERSION = 2 // ← sube versión si ya tenías instalada la app
        private const val TABLE_HISTORICOS = "historicos"
        private const val TABLE_USUARIO = "usuario"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableHistoricos = """
            CREATE TABLE $TABLE_HISTORICOS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                fecha TEXT,
                hora TEXT,
                imc REAL,
                estado TEXT,
                usuario TEXT
            )
        """.trimIndent()

        val createTableUsuario = """
            CREATE TABLE $TABLE_USUARIO (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT
            )
        """.trimIndent()

        db.execSQL(createTableHistoricos)
        db.execSQL(createTableUsuario)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORICOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIO")
        onCreate(db)
    }

    // -------------------------
    // Funciones para usuario
    // -------------------------
    fun guardarUsuario(nombre: String) {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_USUARIO") // Solo permitimos un usuario activo
        val values = ContentValues().apply {
            put("nombre", nombre)
        }
        db.insert(TABLE_USUARIO, null, values)
        db.close()
    }

    fun obtenerUsuario(): String? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT nombre FROM $TABLE_USUARIO LIMIT 1", null)
        var nombre: String? = null
        if (cursor.moveToFirst()) {
            nombre = cursor.getString(0)
        }
        cursor.close()
        db.close()
        return nombre
    }

    // -------------------------
    // Funciones para historicos
    // -------------------------
    fun insertarRegistro(fecha: String, hora: String, imc: Float, estado: String, usuario: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("fecha", fecha)
            put("hora", hora)
            put("imc", imc)
            put("estado", estado)
            put("usuario", usuario)
        }
        db.insert(TABLE_HISTORICOS, null, values)
        db.close()
    }

    fun obtenerRegistros(): List<String> {
        val lista = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORICOS ORDER BY id DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
                val hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"))
                val imc = cursor.getFloat(cursor.getColumnIndexOrThrow("imc"))
                val estado = cursor.getString(cursor.getColumnIndexOrThrow("estado"))
                val usuario = cursor.getString(cursor.getColumnIndexOrThrow("usuario"))

                lista.add("Usuario: $usuario\n$fecha $hora\nIMC: %.2f - $estado".format(imc))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }
}
