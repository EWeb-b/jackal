package com.leeds.jackal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    private val TAG: String = com.leeds.jackal.Login::class.java.getSimpleName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)



        val forgotPasswordBtn =
            findViewById<View>(R.id.forgotPasswordBtn) as Button
        forgotPasswordBtn.setOnClickListener {
            val email = findViewById<View>(R.id.emailForReset) as EditText

            if (email.text.toString().isNotEmpty()) {
                mAuth.sendPasswordResetEmail(email.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email sent.")
                        }
                    }
            } else {
                Toast.makeText(
                    applicationContext,
                    "You must provide an email address!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }
}
