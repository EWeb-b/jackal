package com.leeds.jackal

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private val photos: ArrayList<Photo>

class RecyclerAdapter(private val photos: ArrayList<Photo>) RecyclerView.Adapter<RecyclerAdapter.PhotoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.PhotoHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        return PhotoHolder(inflatedView)
    }

    override fun getItemCount() = photos.size
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.PhotoHolder, position: Int) {
        val itemPhoto = photos[position]
        holder.bindPhoto(itemPhoto)
    }
}

//1
class PhotoHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
    //2
    private var view: View = v
    private var photo: Photo? = null

    //3
    init {
        v.setOnClickListener(this)
    }

    //4
    override fun onClick(v: View) {
        val context = itemView.context
        val showPhotoIntent = Intent(context, PhotoActivity::class.java)
        showPhotoIntent.putExtra(PHOTO_KEY, photo)
        context.startActivity(showPhotoIntent)
    }

    companion object {
        //5
        private val PHOTO_KEY = "PHOTO"
    }

    fun bindPhoto(photo: Photo) {
        this.photo = photo
//        Glide.with(this).load(food.photo.thumb).into(image)
        Glide.with(view.context).load(photo.url).into(view.itemImage)
        view.itemDate.text = photo.humanDate
        view.itemDescription.text = photo.explanation
    }
}