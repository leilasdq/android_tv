package com.example.movietv.presenter

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.example.movietv.model.Movie

class DetailPresenter: AbstractDetailsDescriptionPresenter() {
    override fun onBindDescription(vh: ViewHolder?, item: Any?) {
        val movie =
            item as? Movie
        if (movie != null) {
            vh?.title?.text = movie.title
            vh?.subtitle?.text = "This is a subTitle for ${movie.title}"
            vh?.body?.text = movie.detail
        }
    }
}