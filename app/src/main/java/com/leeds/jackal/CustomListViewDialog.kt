package com.leeds.jackal

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.layout_recipe_dialog.*


class CustomListViewDialog(var activity: Activity, internal var adapter: RecyclerView.Adapter<*>, var ingredients:ArrayList<String>) : Dialog(activity),
    View.OnClickListener {
    var dialog: Dialog? = null

    val user = FirebaseAuth.getInstance().currentUser
    internal var recyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_recipe_dialog)

        recyclerView = findViewById(R.id.recycler_view) as RecyclerView//recycler_view
        recyclerView?.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        recyclerView?.adapter = adapter

        dialog_save_button.setOnClickListener(this)
        dialog_cancel_button.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.dialog_save_button -> {
                val recipeName = edit_recipename.text.toString()
                val serves = edit_serves.text.toString()
                val recipe = Recipe(recipeName,serves.toInt(),ingredients)
                // Write a message to the database
                val database = Firebase.database
                val myRef = database.getReference().child("users").child(user!!.uid).child("recipes").push()

                myRef.setValue(recipe)
                dismiss()
            }
            R.id.dialog_cancel_button -> dismiss()
            else -> {
            }
        }//Do Something
        dismiss()
    }
}
