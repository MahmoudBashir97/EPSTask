package com.mahmoudbashir.epstask.pojo

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)