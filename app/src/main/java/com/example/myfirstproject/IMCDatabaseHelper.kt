package com.example.myfirstproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class IMCDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "imc.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "historicos"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FECHA = "fecha"
        private const val COLUMN_HORA = "hora"
        private const val COLUMN_IMC = "imc"
        private const val COLUMN_ESTADO = "estado"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_FECHA TEXT,
                $COLUMN_HORA TEXT,
                $COLUMN_IMC REAL,
                $COLUMN_ESTADO TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertarRegistro(fecha: String, hora: String, imc: Float, estado: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FECHA, fecha)
            put(COLUMN_HORA, hora)
            put(COLUMN_IMC, imc)
            put(COLUMN_ESTADO, estado)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun obtenerRegistros(): List<String> {
        val lista = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ID DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA))
                val hora = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HORA))
                val imc = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_IMC))
                val estado = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESTADO))

                lista.add("$fecha $hora\nIMC: %.2f - $estado".format(imc))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }
}
