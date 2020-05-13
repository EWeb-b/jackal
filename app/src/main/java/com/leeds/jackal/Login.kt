package com.leeds.jackal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
//    var storedUsername: String? = null
//    var storedPassword: String? = null
    val mAuth = FirebaseAuth.getInstance()
    private val TAG: String = com.leeds.jackal.Login::class.java.getSimpleName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val forgotPasswordBtn =
            findViewById<View>(R.id.forgotPasswordBtn) as Button
        forgotPasswordBtn.setOnClickListener {
            val startFPIntent =
                Intent(applicationContext, ForgotPassword::class.java)
            startActivity(startFPIntent)
        }

        val loginSubmitBtn =
            findViewById<View>(R.id.loginSubmitBtn) as Button
        loginSubmitBtn.setOnClickListener {
            val email =
                findViewById<View>(R.id.emailLoginEntry) as EditText
            val password =
                findViewById<View>(R.id.passwordLoginEntry) as EditText

            mAuth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val mainMenuIntent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(mainMenuIntent)
                        finish()
                        Log.d(TAG, "signInWithEmail:success")
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed. Please try again!",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
