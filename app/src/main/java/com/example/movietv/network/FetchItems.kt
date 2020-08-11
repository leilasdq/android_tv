package com.example.movietv.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.movietv.model.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FetchItems {
    private const val BASE_URL = "http://oveissi.ir/android_tv.php/"
    private var mRetrofit: Retrofit
    private var mApiService: MovieServices

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mApiService = mRetrofit.create(
            MovieServices::class.java)
    }

    fun getAll(): MutableLiveData<Category> {
        val call = mApiService.getAll()
        val data = MutableLiveData<Category>()

        call.enqueue(object : Callback<Category>{
            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                data.value = response.body()
            }
            override fun onFailure(call: Call<Category>, t: Throwable) {
                Log.e("fetchhhhhhhhhhhhh", "Error: ${t.message}")
            }
        })
        return data
    }
}
