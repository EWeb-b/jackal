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

class Register : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    private val TAG: String = com.leeds.jackal.Login::class.java.getSimpleName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton =
            findViewById<View>(R.id.completeRegistration) as Button
        registerButton.setOnClickListener {
            val email =
                findViewById<View>(R.id.emailRegisterEntry) as EditText
            val password =
                findViewById<View>(R.id.passwordRegisterEntry) as EditText
            val repeatPassword =
                findViewById<View>(R.id.passwordCheckerRegisterEntry) as EditText
            val phone =
                findViewById<View>(R.id.phoneRegisterEntry) as EditText

            // password must be longer than 6 chars - add if statement
            if (email.text.toString().isNotEmpty()) {
                if (password.text.toString().length >= 6) {
                    if (password.text.toString() == repeatPassword.text.toString()) {
                        mAuth.createUserWithEmailAndPassword(
                            email.text.toString(),
                            password.text.toString()
                        )
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success")
                                    //                            val user = auth.currentUser
                                    //                            updateUI(user)
                                    val mainMenuIntent =
                                        Intent(applicationContext, MainActivity::class.java)
                                    startActivity(mainMenuIntent)
                                    finish()
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                    Toast.makeText(
                                        baseContext, "Registration failed. Please Try again",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //                            updateUI(null)
                                }

                            }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Those passwords don't match!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Passwords must be at least 6 characters long!",
                        Toast.LENGTH_SHORT
                    ).show()
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
