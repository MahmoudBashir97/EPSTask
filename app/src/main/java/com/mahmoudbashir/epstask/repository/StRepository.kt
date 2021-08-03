package com.mahmoudbashir.epstask.repository

import androidx.lifecycle.LiveData
import com.mahmoudbashir.epstask.pojo.DataModel
import com.mahmoudbashir.epstask.pojo.NewsResponse
import com.mahmoudbashir.epstask.retrofit.RetrofitInstance
import com.mahmoudbashir.epstask.room.StDatabase
import retrofit2.Response

class StRepository(private val db:StDatabase):InterfaceStRepository
{
    override suspend fun insert(model: DataModel) = db.getDao().insert(model)

    override fun getAllStoredData(): LiveData<List<DataModel>> = db.getDao().getAllDataStored()

    override fun deleteItem(model: DataModel)  = db.getDao().deleteItem(model)
    override suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse> {
            return RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)
    }
}