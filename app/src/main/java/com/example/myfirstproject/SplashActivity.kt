package com.example.myfirstproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Espera 4 segundos y luego decide a qué pantalla ir
        Handler(Looper.getMainLooper()).postDelayed({
            val dbHelper = IMCDatabaseHelper(this)
            val usuario = dbHelper.obtenerUsuario()

            val intent = if (usuario == null) {
                Intent(this, RegistrarUsuarioActivity::class.java)
            } else {
                Intent(this, MainActivity::class.java)
            }

            startActivity(intent)
            finish() // Cierra el Splash para que no regrese al presionar "back"
        }, 1000) // ← aquí pon 4000 si quieres 4 segundos, ahora tienes 2000 (2 seg)
    }
}
