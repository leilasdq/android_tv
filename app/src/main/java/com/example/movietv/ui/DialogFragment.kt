package com.example.movietv.ui

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import com.example.movietv.R
import com.example.movietv.model.Movie

class DialogFragment: GuidedStepSupportFragment() {
    private lateinit var selectedMovie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedMovie = activity?.intent?.getParcelableExtra<Parcelable>(DetailFragment.EXTRA_MOVIE) as Movie
    }

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        return GuidanceStylist.Guidance("Do you wanna continue like it?",
            "You have watched the movie before, Watch first or continue?",
            null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_ideogram))
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        actions.add(0, GuidedAction.Builder(requireContext()).id(0).icon(R.drawable.ic_baseline_watch_24).title("Continue watching").build())
        actions.add(1, GuidedAction.Builder(requireContext()).id(1).icon(R.drawable.ic_baseline_favorite_24).title("Like it anyway").build())
    }

    override fun onGuidedActionClicked(action: GuidedAction?) {
        when(action?.id){
            0L -> if(selectedMovie.link != "") {
                startActivity(
                    Intent(activity, PlayerActivity::class.java).putExtra(
                        DetailFragment.EXTRA_MOVIE,
                        selectedMovie
                    )
                )
            } else {
                Toast.makeText(requireContext(), "No link to play...", Toast.LENGTH_SHORT).show()
            }
            1L -> Toast.makeText(requireContext(), "You Added this to your favorite list.", Toast.LENGTH_LONG).show()
        }
    }
}