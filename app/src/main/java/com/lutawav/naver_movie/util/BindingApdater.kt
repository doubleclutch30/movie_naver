package com.lutawav.naver_movie.util

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lutawav.naver_movie.data.model.Movie
import com.lutawav.naver_movie.ui.MovieAdapter
import com.lutawav.naver_movie.R

@BindingAdapter("htmlText")
fun TextView.setText(html: String) {
    text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
}

@BindingAdapter("urlImage")
fun ImageView.setUrlImage(url: String) {
    Glide.with(this).load(url)
        .placeholder(R.drawable.ic_favorite)
        .into(this)
}

@BindingAdapter("setItems")
fun RecyclerView.setAdapterItems(items: List<Movie>?) {
    with((adapter as MovieAdapter)) {
        clear()
        Log.d("setAdapterItems", items.toString())
        items?.let { addItems(it) }
    }
}