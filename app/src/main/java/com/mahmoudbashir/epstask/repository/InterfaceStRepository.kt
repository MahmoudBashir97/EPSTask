package com.mahmoudbashir.epstask.repository

import androidx.lifecycle.LiveData
import com.mahmoudbashir.epstask.pojo.DataModel
import com.mahmoudbashir.epstask.pojo.NewsResponse
import retrofit2.Response

interface InterfaceStRepository {
    suspend fun insert(model: DataModel)

    fun getAllStoredData():LiveData<List<DataModel>>

    fun deleteItem(model: DataModel)

    suspend fun getBreakingNews(countryCode:String,pageNumber:Int): Response<NewsResponse>

}