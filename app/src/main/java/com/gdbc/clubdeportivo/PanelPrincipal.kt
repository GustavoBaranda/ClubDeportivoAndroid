package com.gdbc.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gdbc.clubdeportivo.databinding.PanelPrincipalBinding
import com.google.android.material.navigation.NavigationView

class PanelPrincipal : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: PanelPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PanelPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarPanelPrincipal.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_panel_principal)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_ingresar_cliente, R.id.nav_visualizar_carnet,
                R.id.nav_comprobante, R.id.nav_listar_cuotas_vencidas, R.id.nav_abonar
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val userRole = intent.getStringExtra(Login.USER_ROL)

        val menu = navView.menu

        if (userRole == "empleado") {
            menu.findItem(R.id.nav_visualizar_carnet).isVisible = false
            menu.findItem(R.id.nav_comprobante).isVisible = false
        } else if (userRole == "cliente") {
            menu.findItem(R.id.nav_ingresar_cliente).isVisible = false
            menu.findItem(R.id.nav_comprobante).isVisible = false
            menu.findItem(R.id.nav_listar_cuotas_vencidas).isVisible = false
            menu.findItem(R.id.nav_abonar).isVisible = false
        }

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.panel_principal, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment_content_panel_principal)
        when (item.itemId) {
            R.id.nav_cerrar_sesion -> {
                logout()
                return true
            }

            R.id.nav_visualizar_carnet -> {
                val userId = intent.getIntExtra(Login.USER_ID, -1)
                val userRol = intent.getStringExtra(Login.USER_ROL)
                val bundle = Bundle().apply {
                    putInt("USER_ID", userId)
                    putString("USER_ROL", userRol)
                }
                navController.navigate(R.id.nav_visualizar_carnet, bundle)
            }

            else -> {
                navController.navigate(item.itemId)
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout() {
        val intent = Intent(this, Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_panel_principal)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
