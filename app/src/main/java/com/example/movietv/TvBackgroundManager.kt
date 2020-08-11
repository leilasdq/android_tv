package com.example.movietv

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.leanback.app.BackgroundManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class TvBackgroundManager(private val activity: Activity) {
    var backgroundManager = BackgroundManager.getInstance(activity).apply {
        attach(activity.window)
    }

    val default = activity.getDrawable(R.drawable.background)

    fun updateBackground(drawable: Drawable){
        backgroundManager.drawable = drawable
    }

    fun updateBackground(imageUrl: String){
        Glide.with(activity)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>(){
                override fun onLoadCleared(placeholder: Drawable?) {            }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    backgroundManager.setBitmap(resource)
                }
            })
    }

    fun clearBackground(){
        backgroundManager.drawable = default
    }
}
