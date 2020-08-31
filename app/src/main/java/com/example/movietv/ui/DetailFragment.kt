package com.example.movietv.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.movietv.R
import com.example.movietv.model.Movie
import com.example.movietv.presenter.DetailPresenter

private const val DETAIL_THUMB_WIDTH = 300
private const val DETAIL_THUMB_HEIGHT = 400

class DetailFragment : DetailsSupportFragment() {

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }

    private val detailFullOverviewPresenter =
        FullWidthDetailsOverviewRowPresenter(DetailPresenter())
    lateinit var detailOverviewRow: DetailsOverviewRow
    lateinit var selectedMovie: Movie

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        selectedMovie = activity?.intent?.getParcelableExtra<Parcelable>(EXTRA_MOVIE) as Movie
        detailOverviewRow = DetailsOverviewRow(selectedMovie)

        setMovieThumbnail(selectedMovie)
        setActionAdapter()
        setupPresenter()
    }

    private fun setMovieThumbnail(selectedMovie: Movie) {
        Glide.with(activity!!)
            .asBitmap()
            .load(selectedMovie.picUrl)
            .apply(RequestOptions().override(DETAIL_THUMB_WIDTH, DETAIL_THUMB_HEIGHT).centerCrop())
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    detailOverviewRow.setImageBitmap(activity, resource)
                }
            })
    }

    private fun setActionAdapter() {
        val actionAdapter = ArrayObjectAdapter().apply {
            add(Action(0, "Play").apply {
                icon = resources.getDrawable(R.drawable.ic_baseline_play_circle_filled_24)
            })
            add(Action(1, "Like").apply {
                icon = resources.getDrawable(R.drawable.ic_baseline_favorite_24)
            })
        }
        detailOverviewRow.actionsAdapter = actionAdapter

        onItemViewClickedListener =
            OnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->
                if (item is Action) {
                    if (item.id.toInt() == 0) {
                        if (selectedMovie.link != "") {
                            startActivity(
                                Intent(activity, PlayerActivity::class.java).putExtra(
                                    EXTRA_MOVIE,
                                    selectedMovie
                                )
                            )
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "No link to play...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else if (item.id.toInt() == 1) {
                        startActivity(
                            Intent(activity, DialogActivity::class.java).putExtra(
                                EXTRA_MOVIE,
                                selectedMovie
                            )
                        )
                    }
                }
            }
    }

    private fun setupPresenter() {
        val classPresenterSelector = ClassPresenterSelector().apply {
            addClassPresenter(DetailsOverviewRow::class.java, detailFullOverviewPresenter)
            addClassPresenter(ListRow::class.java, ListRowPresenter())
        }

        val detailPageAdapter = ArrayObjectAdapter(classPresenterSelector).apply {
            add(detailOverviewRow)
        }

        setAdapter(detailPageAdapter)
    }
}