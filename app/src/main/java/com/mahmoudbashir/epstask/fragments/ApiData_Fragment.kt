package com.mahmoudbashir.epstask.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mahmoudbashir.epstask.R
import com.mahmoudbashir.epstask.adapters.NewsAdapter
import com.mahmoudbashir.epstask.databinding.FragmentApiDataBinding
import com.mahmoudbashir.epstask.ui.MainActivity
import com.mahmoudbashir.epstask.viewModel.StViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class ApiData_Fragment : Fragment() {
    lateinit var dataBinding:FragmentApiDataBinding
    lateinit var adapterNews:NewsAdapter
    lateinit var viewModel:StViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_api_data_, container, false)
        //Initializing ViewModel
        viewModel = (activity as MainActivity).viewModel
        //initializing RecyclerView
        setUpRecyclerView()
        //getting data from api
        fetchingApiNews()

        return dataBinding.root
    }

    private fun fetchingApiNews() {
        dataBinding.isLoading = true
        viewModel.breakingNews.observe(viewLifecycleOwner,{response->
            if (response != null){
                response.data?.let {
                    dataBinding.isLoading = false

                    adapterNews.differ.submitList(it.articles.toList())
                    adapterNews.notifyDataSetChanged()
                }
            }
        })
    }

    private fun setUpRecyclerView() {
        adapterNews = NewsAdapter()
        dataBinding.recApi.apply {
            setHasFixedSize(true)
            adapter = adapterNews
        }
    }
}