package com.leeds.jackal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ChangePassword : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    private val TAG: String = com.leeds.jackal.ChangePassword::class.java.getSimpleName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val user = mAuth.currentUser

        val changePasswordBtn =
            findViewById<View>(R.id.changePasswordBtn) as Button
        changePasswordBtn.setOnClickListener {
            val newPassword = findViewById<View>(R.id.passwordForReset) as EditText

            if (newPassword.text.toString().isNotEmpty()) {
                user!!.updatePassword(newPassword.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "User password updated.")
                            Toast.makeText(
                                applicationContext,
                                "Your password has been changed!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "We couldn't change your password. Please try again!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    applicationContext,
                    "You must provide a new password!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
