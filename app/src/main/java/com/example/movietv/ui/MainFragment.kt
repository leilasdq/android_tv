package com.example.movietv.ui

import android.os.Bundle
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.lifecycle.Observer
import com.example.movietv.FetchItems
import com.example.movietv.presenter.MoviePresenter
import com.example.movietv.R
import com.example.movietv.model.Movy

class MainFragment: BrowseSupportFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        brandColor = resources.getColor(R.color.brandColor)
        title = "Movies"
        showTitle(true)
        headersState = HEADERS_ENABLED

        setAdapter()
    }

    private fun setAdapter(){
        val listAdapter = ArrayObjectAdapter(ListRowPresenter())
        val moviePresenter = MoviePresenter(requireContext())

        fun getList(){
            FetchItems.getAll().observe(this, Observer {
                for ((index, element) in it.withIndex()){
                    val header = HeaderItem(index.toLong(), element.category_name)
                    val movieAdapter = ArrayObjectAdapter(moviePresenter)

                    for (item in element.movies){
                        movieAdapter.add(Movy(item.link, item.title))
                    }

                    listAdapter.add(ListRow(header, movieAdapter))
                }

                adapter = listAdapter
            })
        }

        getList()
    }
}