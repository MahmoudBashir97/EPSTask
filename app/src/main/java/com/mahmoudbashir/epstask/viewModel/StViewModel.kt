package com.mahmoudbashir.epstask.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mahmoudbashir.epstask.EpsApplication
import com.mahmoudbashir.epstask.pojo.DataModel
import com.mahmoudbashir.epstask.pojo.NewsResponse
import com.mahmoudbashir.epstask.repository.StRepository
import com.mahmoudbashir.epstask.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class StViewModel(val app:Application,private val repo:StRepository):AndroidViewModel(app) {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakinNewsPage = 2
    var breakingNewsResponse : NewsResponse? = null

    init {
        getBreakingNews("us")
    }

    fun insertData(data:DataModel) = viewModelScope.launch {
        repo.insert(data)
    }

    fun getAllStoredData() = repo.getAllStoredData()

    fun deleteItem(data: DataModel) = viewModelScope.launch {
        repo.deleteItem(data)
    }



    fun getBreakingNews(countryCode:String) = viewModelScope.launch {
        safeBreakingNewsCall(countryCode)
    }
    private suspend fun safeBreakingNewsCall(countryCode: String){

        breakingNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = repo.getBreakingNews(countryCode, breakinNewsPage)
                breakingNews.postValue(handleBreakingNewsResponse((response)))
            }else{
                breakingNews.postValue(Resource.Error("No Internet Connection"))
            }
        }catch (t : Throwable){
            when(t){
                is IOException -> breakingNews.postValue(Resource.Error("Network Failure"))
                else -> breakingNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {resultResponse ->
                breakinNewsPage++
                if (breakingNewsResponse == null)
                {
                    breakingNewsResponse = resultResponse
                }else{
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse?:resultResponse) }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection():Boolean{

        val connectivityManager = getApplication<EpsApplication>().getSystemService(
                Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)?: return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}