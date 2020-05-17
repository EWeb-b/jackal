package com.leeds.jackal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var drawer: DrawerLayout? = null
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout

        navigationView.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            Log.v("TAG", " clicked")
            when (id) {
                R.id.nav_home -> {
                    // Handle the home action
                }
                R.id.nav_orders -> {
                }
                R.id.nav_cart -> {
                }
                R.id.nav_payment -> {
                }
                R.id.nav_help -> {
                    //sendNetworkRequest();
                }
                R.id.nav_changePassword -> {
                    val changePasswordIntent =
                        Intent(applicationContext, ChangePassword::class.java)
                    startActivity(changePasswordIntent)
                    finish()
                }
                R.id.nav_logOut -> {
                    mAuth.signOut()
                    val logOutIntent =
                        Intent(applicationContext, StartMenu::class.java)
                    startActivity(logOutIntent)
                    finish()
                }
            }
            drawer!!.closeDrawer(GravityCompat.START)
            true
        }

    }


//    private fun replaceFragment(fragment: Fragment){
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
//        fragmentTransaction.commit()
//
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
