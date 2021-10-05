package com.marvelapp.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.marvelapp.base.App

object AppUtil {

    fun hideKeyboard(view: View) {
        view.clearFocus()
        val inputMethodManager: InputMethodManager? =
            App.INSTANCE.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun loadImage(view: ImageView?, url: String?) {
        if (view != null && url != null) {
            Glide.with(App.INSTANCE)
                .load(url)
                .apply(
                    RequestOptions()
                        .centerInside()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(view)
        }
    }
}