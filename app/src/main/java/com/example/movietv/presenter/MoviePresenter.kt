package com.example.movietv.presenter

import android.content.Context
import android.view.ViewGroup
import androidx.leanback.widget.BaseCardView
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movietv.R
import com.example.movietv.model.Movie

class MoviePresenter(private val context: Context): Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view = ImageCardView(context).apply {
            isFocusable = true
            cardType = BaseCardView.CARD_TYPE_INFO_UNDER_WITH_EXTRA
            infoVisibility = BaseCardView.CARD_REGION_VISIBLE_ALWAYS
            setMainImageDimensions(300, 150)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        (item as? Movie)?.let { movie->
            (viewHolder?.view as? ImageCardView)?.apply {
                titleText = movie.title
                contentText = movie.detail
                Glide.with(context).load(movie.picUrl).apply(RequestOptions().centerCrop()).into(this.mainImageView)
                badgeImage = resources.getDrawable(R.drawable.ic_baseline_play_circle_filled_24)
            }
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}