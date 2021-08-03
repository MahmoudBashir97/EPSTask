package com.mahmoudbashir.epstask.retrofit

import com.mahmoudbashir.epstask.pojo.NewsResponse
import com.mahmoudbashir.epstask.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
            @Query("country")
            countryCode : String ="us",
            @Query("page")
            pageNumber : Int = 1,
            @Query("apiKey")
            apiKey:String = Constants.API_KEY
    ): Response<NewsResponse>
}