package com.example.movietv.model

data class Movy(
    val link: String,
    val title: String,
    val detail: String = "Top Ten movie in this genre",
    val picUrl: String = "https://www.denofgeek.com/wp-content/uploads/2016/05/cobra_poster.jpg?resize=620%2C349"
)