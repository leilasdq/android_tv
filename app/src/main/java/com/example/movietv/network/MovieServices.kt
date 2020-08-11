package com.example.movietv.network

import com.example.movietv.model.Category
import com.example.movietv.model.CategoryItem
import retrofit2.Call
import retrofit2.http.GET

interface MovieServices {
    @GET(".")
    fun getAll(): Call<Category>
}