package com.leeds.jackal

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage

import java.io.File
import java.io.IOException
import java.util.*

class PhotoRecipeActivity : AppCompatActivity() {



    lateinit var textBlock :TextView
    lateinit var image : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_recipe)


        val photoButton = findViewById<Button>(R.id.camera_button)
        textBlock = findViewById<TextView>(R.id.photoText)
        image =  findViewById(R.id.testPhoto)

        photoButton.setOnClickListener {
            dispatchTakePictureIntent()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            val imgFile = File(currentPhotoPath)
            if (imgFile.exists()){
                val uri = Uri.fromFile(imgFile)
                image.setImageURI(uri)
                runTextRecognition(getImgFromPath(this@PhotoRecipeActivity,uri))
            }
            image.setImageBitmap(BitmapFactory.decodeFile(currentPhotoPath))

        }
    }

    val REQUEST_TAKE_PHOTO = 1

    //Use the existing camera app on the phone to
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.leeds.jackal.fileprovider",
                        it
                    )

//                    val path = photoURI.path
//                    var textView= findViewById<TextView>(R.id.photoText)
//                    textView.text = path
//                    textView.visibility = View.VISIBLE
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }


    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {

        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun getImgFromPath(context: Context,uri: Uri):FirebaseVisionImage?{
        val image: FirebaseVisionImage
        try {
            image = FirebaseVisionImage.fromFilePath(context,uri)
            return image
        } catch (e: IOException){
            e.printStackTrace()
        }
        return null
    }


    //Run initial text recognition on the photo
    private fun runTextRecognition(image : FirebaseVisionImage?){
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer
        if (image != null){
            val result = detector.processImage(image)
                .addOnSuccessListener { firebaseVisionText ->

                    textBlock.setText( firebaseVisionText.text)
                    textBlock.visibility = View.VISIBLE
//                    for (block in firebaseVisionText.textBlocks){
//                        val boundingBox = block.boundingBox
//                        val text = block.text
//
//                        for (line in block.lines){
//
//                        }
//                    }
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    e.printStackTrace()
                }

        }

    }
}
