package com.leeds.jackal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    private val TAG: String = com.leeds.jackal.Login::class.java.getSimpleName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handler = Handler()
        handler.postDelayed(Runnable {
            if (mAuth.currentUser != null) {
                // user is signed in - redirect to main activity
                val mainMenuIntent = Intent(applicationContext, MainActivity::class.java)
                Log.d(TAG, "Success: User is already logged in!")
                startActivity(mainMenuIntent)
                finish()
            } else {
                // user is not - redirect to start Menu
                val startMenuIntent = Intent(applicationContext, StartMenu::class.java)
                startActivity(startMenuIntent)
                finish()
            }
        }, 2000)


    }
}
