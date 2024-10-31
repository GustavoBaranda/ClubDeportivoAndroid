package com.gdbc.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn: Button = findViewById(R.id.btnLogin)
        val userLogin: EditText = findViewById(R.id.userLogin)
        val passwordLogin: EditText = findViewById(R.id.passwordLogin)

        btn.setOnClickListener {
            val username = userLogin.text.toString()
            val password = passwordLogin.text.toString()

            if (username == "" && password == "") {
                val intent = Intent(this, PanelPrincipal::class.java)
                startActivity(intent)
                finish() //
            } else {
                Toast.makeText(this, "Usuario y/o Contraseña invalida", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
