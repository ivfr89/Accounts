package com.fernandez.players.core.extensions

import android.view.View
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fernandez.players.R

fun ImageView.loadImage(image: String?) {

    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()


    val options = RequestOptions()
         .placeholder(circularProgressDrawable)
        .error(R.drawable.ic_profile)


    Glide.with(this).load(image).apply(options).into(this)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hidde() {
    this.visibility = View.GONE
}