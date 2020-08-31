package com.example.movietv.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.leanback.app.ErrorSupportFragment
import com.example.movietv.R

class ErrorViewFragment: ErrorSupportFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Oooops :("
        imageDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_warning_24)
        message = "NO link available!"
        setDefaultBackground(false)
        buttonText = "Back to home"
        buttonClickListener = View.OnClickListener {
            activity?.finish()
        }
    }
}