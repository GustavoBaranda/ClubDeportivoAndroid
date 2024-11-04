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
import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.repository.MorosoRepository
import com.gdbc.clubdeportivo.data.repository.PagoRepository
import com.gdbc.clubdeportivo.data.repository.UsuarioRepository

class Login : AppCompatActivity() {

    companion object{
        const val USER_ID = "USER_ID"
        const val USER_ROL = "USER_ROL"
    }

    private lateinit var dbHelper: BDatos
    private lateinit var morosoRepository: MorosoRepository
    private lateinit var pagoRepository: PagoRepository
    private lateinit var usuarioRepository: UsuarioRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initDB()

        val btn: Button = findViewById(R.id.btnLogin)
        val userLogin: EditText = findViewById(R.id.userLogin)
        val passwordLogin: EditText = findViewById(R.id.passwordLogin)

        btn.setOnClickListener {
            val username = userLogin.text.toString()
            val password = passwordLogin.text.toString()

            login(username,password)
        }
    }

    private fun initDB() {
        dbHelper = BDatos(this)
        morosoRepository = MorosoRepository(dbHelper)
        pagoRepository = PagoRepository(dbHelper)
        usuarioRepository = UsuarioRepository(dbHelper)
        morosoRepository.agregarNuevosMorosos(pagoRepository)
    }

    private fun login(username: String, password: String) {
        if (username.isNotBlank() && password.isNotBlank()) {
            val user = usuarioRepository.loguearse(username, password)

            if (user != null) {
                val intent = Intent(this, PanelPrincipal::class.java)
                intent.putExtra(USER_ROL, user.rol)
                intent.putExtra(USER_ID, user.idUsuario)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Usuario y/o Contraseña inválida", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(
                this,
                "Ingrese el usuario y la contraseña",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}
