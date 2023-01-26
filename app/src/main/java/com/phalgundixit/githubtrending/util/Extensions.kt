package com.phalgundixit.githubtrending.util

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat


/**
 * Creates an extension property on [Context] that gets color resource.
 */
fun Context.color(id: Int): Int = ContextCompat.getColor(this, id)

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun TextView.setDrawableBackgroundColor(color: String) {
    compoundDrawables[0]?.let {
        val wrappedDrawable = DrawableCompat.wrap(it)
        try {
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor(color))
        } catch (ignore: IllegalArgumentException) {
//            AppLogger.logD("setDrawableBackgroundColor {}", "Unknown color")
        }
    }
}

fun ImageView.circleCrop(url: String) {
//    GlideApp.with(context)
//        .load(url).placeholder(R.drawable.drawable_round_holder)
//        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//        .apply(RequestOptions().circleCrop())
//        .into(this)
}