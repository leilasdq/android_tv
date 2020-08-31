package com.example.movietv.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.movietv.R

class ErrorActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)

        val mErrorFragment = ErrorViewFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_error_root, mErrorFragment)
            .commit()
    }
}