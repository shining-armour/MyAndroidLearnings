package com.example.mars_photos_app.ui

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mars_photos_app.R
import com.example.mars_photos_app.adapter.MarsPhotosAdapter
import com.example.mars_photos_app.network.MarsPhoto
import com.example.mars_photos_app.overview.MarsApiStatus


/**
 * First parameter is the type of the target View and the second is the value being set to the attribute.
 * Inside the method, the Coil library loads the image off the UI thread and sets it into the ImageView.
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl:String?){
    imgUrl?.let {
//        val imgUri1:Uri = imgUrl.toUri()
        /**
         * convert the URL string to a Uri object using the toUri() method.
         * To use the HTTPS scheme, append buildUpon.scheme("https") to the toUri builder.
         * Call build() to finally build a nullable URI object.
         */
        val imgUri2: Uri? = imgUrl.toUri().buildUpon().scheme("https").build()
//        Log.i("Image Uri 1", imgUri1.toString())
//        Log.i("Image Uri: ", imgUri2.toString())
        /**
         * Load the image from the imgUri2 object into the imgView.
         * If image has not yet loaded, use placeholder drawable.
         * If image failed to load, use error drawable
         */
        imgView.load(imgUri2){
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

/**
 * Annotate that method with a @BindingAdapter with listData attribute.
 * Takes a RecyclerView and a list of MarsPhoto objects as arguments.
 */
@BindingAdapter("listOfData")
fun bindRecyclerView(recyclerView: RecyclerView, marsPhotosList: List<MarsPhoto>?){
    val adapter = recyclerView.adapter as MarsPhotosAdapter
    /**
     * This tells the RecyclerView when a new list is available with updates.
     */
    adapter.submitList(marsPhotosList)
}

/**
 * When the app is in the loading state or the error state, the ImageView should be visible.
 * When the app is done loading, the ImageView should be invisible.
 */
@BindingAdapter("marsApiStatus")
fun bindStatus(statusImgView: ImageView, status: MarsApiStatus?){
    when(status){
        MarsApiStatus.LOADING -> {
            statusImgView.visibility = View.VISIBLE
            statusImgView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.FAILURE -> {
            statusImgView.visibility = View.VISIBLE
            statusImgView.setImageResource(R.drawable.ic_cloud_off)
        }
        MarsApiStatus.SUCCESS -> {
            statusImgView.visibility = View.GONE
        }
    }
}