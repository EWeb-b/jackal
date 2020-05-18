package com.leeds.jackal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class StartMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_menu)
        val goToLoginActivity =
            findViewById<View>(R.id.goToLoginActivity) as Button
        goToLoginActivity.setOnClickListener {
            val startLoginIntent =
                Intent(applicationContext, Login::class.java)
            startActivity(startLoginIntent)
        }
        val goToRegisterActivity =
            findViewById<View>(R.id.goToRegisterActivity) as Button
        goToRegisterActivity.setOnClickListener {
            val startRegisterIntent =
                Intent(applicationContext, Register::class.java)
            startActivity(startRegisterIntent)
        }
    }
}