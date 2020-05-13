package com.leeds.jackal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {
    var storedUsername: String? = null
    var storedPassword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val forgotPasswordBtn =
            findViewById<View>(R.id.forgotPasswordBtn) as Button
        forgotPasswordBtn.setOnClickListener {
            val startFPIntent =
                Intent(applicationContext, ForgotPasswordActivity::class.java)
            startActivity(startFPIntent)
        }

        val loginSubmitBtn =
            findViewById<View>(R.id.loginSubmitBtn) as Button
        loginSubmitBtn.setOnClickListener {
            val username =
                findViewById<View>(R.id.usernameLoginEntry) as EditText
            val password =
                findViewById<View>(R.id.passwordLoginEntry) as EditText
            storedUsername = username.text.toString()
            storedPassword = password.text.toString()
            login.setUsername(username.text.toString())
            login.setPassword(password.text.toString())
            sendNetworkRequest(login)
        }
    }
}
