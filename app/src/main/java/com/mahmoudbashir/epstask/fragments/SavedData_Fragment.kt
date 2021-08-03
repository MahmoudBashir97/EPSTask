package com.mahmoudbashir.epstask.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.mahmoudbashir.epstask.R
import com.mahmoudbashir.epstask.adapters.SavedDataAdapter
import com.mahmoudbashir.epstask.databinding.FragmentSavedDataBinding
import com.mahmoudbashir.epstask.ui.MainActivity
import com.mahmoudbashir.epstask.viewModel.StViewModel


class SavedData_Fragment : Fragment() {
    lateinit var savedBinding:FragmentSavedDataBinding
    lateinit var savedAdapter: SavedDataAdapter
    lateinit var viewModel : StViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        savedBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_saved_data_, container, false)

        viewModel = (activity as MainActivity).viewModel

        setUpRecyclerView()
        gettingDataFromRoom()

        return savedBinding.root
    }

    private fun setUpRecyclerView() {
        savedBinding.isAdded = false
        savedAdapter = SavedDataAdapter()
        savedBinding.recSavedData.apply {
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL){})
            adapter = savedAdapter
        }
    }

    private fun gettingDataFromRoom(){

        viewModel.getAllStoredData().observe(viewLifecycleOwner,{data->
            if (data !=null)
                savedAdapter.differ.submitList(data)
            savedBinding.isAdded = true
        })
    }


}