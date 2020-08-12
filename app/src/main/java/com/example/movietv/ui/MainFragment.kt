package com.example.movietv.ui

import android.content.Intent
import android.os.Bundle
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.OnItemViewSelectedListener
import androidx.lifecycle.Observer
import com.example.movietv.network.FetchItems
import com.example.movietv.presenter.MoviePresenter
import com.example.movietv.R
import com.example.movietv.TvBackgroundManager
import com.example.movietv.model.Movie

class MainFragment: BrowseSupportFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val tvBackgroundManager = TvBackgroundManager(requireActivity())

        brandColor = resources.getColor(R.color.brandColor)
        title = "Movies"
        showTitle(true)
        headersState = HEADERS_ENABLED

        setAdapter()

        tvBackgroundManager.clearBackground()
        onItemViewSelectedListener = OnItemViewSelectedListener { itemViewHolder, item, rowViewHolder, row ->
            if (item is Movie){
                tvBackgroundManager.updateBackground(item.picUrl)
            } else {
                tvBackgroundManager.clearBackground()
            }
        }
        onItemViewClickedListener = OnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->
            if (item is Movie){
                startActivity(Intent(activity, DetailActivity::class.java).putExtra(DetailFragment.EXTRA_MOVIE, item))
            }
        }

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
                        movieAdapter.add(Movie(item.link, item.title))
                    }

                    listAdapter.add(ListRow(header, movieAdapter))
                }

                val header1 = HeaderItem("Other")
                val newMovieAdapter = ArrayObjectAdapter(moviePresenter)
                newMovieAdapter.add(Movie(
                    "",
                    "Frankenweenie",
                    "Frankenweenie is a 2012 American 3D stop motion-animated supernatural horror comedy film directed by Tim Burton and produced by Walt Disney Pictures.[3] It is a remake of Burton's 1984 short film of the same name and is also both a parody of and homage to the 1931 film Frankenstein, based on Mary Shelley's book of the same name. The voice cast includes four actors who worked with Burton on previous films: Winona Ryder (Beetlejuice and Edward Scissorhands); Martin Short (Mars Attacks!); Catherine O'Hara (Beetlejuice and The Nightmare Before Christmas); and Martin Landau (Ed Wood and Sleepy Hollow), along with some new voice actors, such as Charlie Tahan and Atticus Shaffer.",
                    "https://www.gannett-cdn.com/-mm-/1f8ac36e35875bcd7baab5ef2b330f818b7ad867/c=12-0-588-324/local/-/media/2018/05/14/USATODAY/usatsports/wp-USAT-allthemoms-front1-11182-frankenweenie.jpg"
                ))
                newMovieAdapter.add(Movie(
                    "",
                    "Arthur Christmas",
                    "Arthur Christmas is a 2011 British-American 3D computer-animated Christmas comedy film, produced by Aardman Animations and Sony Pictures Animation as their first collaborative project. The film was released on 11 November 2011, in the UK, and on 23 November 2011, in the US.\n" +
                            "Directed by Sarah Smith and co-directed by Barry Cook,[4] it stars the voices of James McAvoy, Hugh Laurie, Bill Nighy, Jim Broadbent, Imelda Staunton, and Ashley Jensen.",
                    "https://www.gannett-cdn.com/-mm-/b2b05a4ab25f4fca0316459e1c7404c537a89702/c=0-0-1365-768/local/-/media/2018/06/11/USATODAY/usatsports/247WallSt.com-247WS-469696-arthur-christmas.jpg"
                ))
                listAdapter.add(ListRow(header1, newMovieAdapter))

                adapter = listAdapter
            })
        }

        getList()
    }
}