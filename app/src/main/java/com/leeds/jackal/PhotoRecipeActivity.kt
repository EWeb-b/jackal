package com.leeds.jackal


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_photo_recipe.*
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class PhotoRecipeActivity : AppCompatActivity() {




    lateinit var textBlock :TextView
    lateinit var image : ImageView
    lateinit var galleryButton: ImageButton
    lateinit var photoButton: Button
    private var ingredients: ArrayList<String> = ArrayList()

    internal var customDialog: CustomListViewDialog? = null

    val WRITE_STORAGE = 100
    val READ_STORAGE = 101
    val SELECT_PHOTO = 102
    val REQUEST_TAKE_PHOTO = 2



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_recipe)

        //Initialise and inflate activity views
        photoButton = findViewById(R.id.camera_button)
        textBlock = findViewById(R.id.photoText)
        image =  findViewById(R.id.testPhoto)
        galleryButton = findViewById(R.id.gallery_button)

        setupPermissions()

        photoButton.setOnClickListener {
            dispatchTakePictureIntent()

        }

        setUpNewImageListener()

    }

    private fun setupPermissions(){
        val permissionRead = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val permissionWrite = ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permissionRead!= PackageManager.PERMISSION_GRANTED){
            Log.i("Access Error:","Permission to access media denied")
            makeRequestRead()
        }
        if (permissionWrite!= PackageManager.PERMISSION_GRANTED){
            Log.i("Access Error:","Permission to access media denied")
            makeRequestWrite()
        }

    }
    private fun makeRequestWrite(){
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            WRITE_STORAGE )
    }
    private fun makeRequestRead(){
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            READ_STORAGE )

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            READ_STORAGE ->{
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Log.i("Access Error:", "Permission denied by user")
                    makeRequestRead();
                }else{
                    Log.i("Access Granted:", "Permission granted by user")

                }
            }
            WRITE_STORAGE ->{
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Log.i("Access Error:", "Permission denied by user")
                    makeRequestWrite();
                }else{
                    Log.i("Access Granted:", "Permission granted by user")

                }
            }
        }
    }


    fun checkPermission(requestCode: Int) {
        when (requestCode) {
            WRITE_STORAGE -> {
                val hasWriteExternalStoragePermission = ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

//If we have access to external storage...//
                if (hasWriteExternalStoragePermission == PackageManager.PERMISSION_GRANTED) {

//...call selectPicture, which launches an Activity where the user can select an image//
                    //selectPicture()

//If permission hasn’t been granted, then...//
                } else {

//...request the permission//
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        requestCode
                    )
                }
            }
        }
    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, returnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, returnedIntent)
        when (requestCode) {
            SELECT_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                returnedIntent?.data?.let {
                    val selectedImageBitmap = resizeImage(it)
                    testPhoto.setImageBitmap(selectedImageBitmap)
                    //Log.d("LIst SIZE0", ingredients.size.toString())
                    val firebaseImg = FirebaseVisionImage.fromBitmap(selectedImageBitmap!!)
                    runTextRecognition(firebaseImg)
                }
            }
            REQUEST_TAKE_PHOTO -> if (resultCode == Activity.RESULT_OK) {

                val imgFile = File(currentPhotoPath)
                if (imgFile.exists()) {
                    galleryAddPic()
                    val uri = Uri.fromFile(imgFile)
                    image.setImageURI(uri)
                    runTextRecognition(getImgFromPath(this@PhotoRecipeActivity, uri))
                }
                image.setImageBitmap(BitmapFactory.decodeFile(currentPhotoPath))
            }
        }


    }


    //Use the existing camera app on the phone to capture an Image
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
        Log.d("Storage Location:", storageDir!!.absolutePath)
        return File.createTempFile(
            "RECIPEZ_${timeStamp}_", /* prefix */
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

    private fun getBitmapFromUri(filePath: Uri): Bitmap? {
        return MediaStore.Images.Media.getBitmap(this.contentResolver, filePath)
    }

    //Run initial text recognition on the photo
    private fun runTextRecognition(image : FirebaseVisionImage?){
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer
        if (image != null){
            val result = detector.processImage(image)
                .addOnSuccessListener { firebaseVisionText ->

                    //Process text

                    //Prompt dialog for user
                    //Dialog start
                    Log.d("Picture Text", firebaseVisionText.text)

                    textBlock.visibility = View.VISIBLE
                    Log.d("Text blocks", firebaseVisionText.textBlocks.size.toString())
                    for (block in firebaseVisionText.textBlocks) {
                        for (line in block.lines){
                            Log.d("LINE TEXT", line.text)
                            ingredients.add(line.text)
                        }
                    }
                    Log.d("LIst SIZE2", ingredients.size.toString())
                    val dataAdapter = RecyclerAdapter(ingredients)
                    for (item in ingredients) {
                        Log.i("LIST CONTENTS: " , item)
                    }
                    customDialog = CustomListViewDialog(this@PhotoRecipeActivity, dataAdapter,ingredients)
                    customDialog!!.show()
                    customDialog!!.setCanceledOnTouchOutside(false)
                    Log.d("List SIZE", ingredients.size.toString())
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    e.printStackTrace()
                }


        }

    }

    private fun resizeImage(selectedImage: Uri): Bitmap? {

        return getBitmapFromUri(selectedImage)?.let {
            val scaleFactor = Math.max(
                it.width.toFloat() / testPhoto.width.toFloat(),
                it.height.toFloat() / testPhoto.height.toFloat()
            )

            Bitmap.createScaledBitmap(
                it,
                (it.width / scaleFactor).toInt(),
                (it.height / scaleFactor).toInt(),
                true
            )
        }
    }

    private fun setUpNewImageListener() {
        galleryButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, SELECT_PHOTO)
        }
    }


    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }



//
//        fun createTempFile(file: File?): File {
//            var file = file
//            val directory = File(
//                Environment.getExternalStorageDirectory()
//                    .path + "/com.jessicathornsby.myapplication"
//            )
//            if (!directory.exists() || !directory.isDirectory) {
//                directory.mkdirs()
//            }
//            if (file == null) {
//                file = File(directory, "orig.jpg")
//            }
//            return file
//        }
//
//        private fun compressPhoto(photoFile: File, bitmap: Bitmap?): Bitmap? {
//            try {
//                val fOutput = FileOutputStream(photoFile)
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fOutput)
//                fOutput.close()
//            } catch (exception: IOException) {
//                exception.printStackTrace()
//            }
//            return bitmap
//        }
//    }
//    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {
//
//        ExifInterface ei = new ExifInterface(selectedImage.getPath());
//        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//
//        switch (orientation) {
//            case ExifInterface.ORIENTATION_ROTATE_90:
//            return rotateImage(img, 90);
//            case ExifInterface.ORIENTATION_ROTATE_180:
//            return rotateImage(img, 180);
//            case ExifInterface.ORIENTATION_ROTATE_270:
//            return rotateImage(img, 270);
//            default:
//            return img;
//        }
//    }
//
//    private static Bitmap rotateImage(Bitmap img, int degree) {
//        Matrix matrix = new Matrix();
//        matrix.postRotate(degree);
//        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
//        img.recycle();
//        return rotatedImg;
//    }






    }
