package com.mahmoudbashir.epstask.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.mahmoudbashir.epstask.R
import com.mahmoudbashir.epstask.adapters.ViewPagerAdapter
import com.mahmoudbashir.epstask.databinding.FragmentViewPagerImagesBinding
import com.mahmoudbashir.epstask.pojo.DataModel
import com.mahmoudbashir.epstask.ui.MainActivity
import com.mahmoudbashir.epstask.viewModel.StViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ViewPagerImages_Fragment : Fragment() {

    lateinit var pagerBinding:FragmentViewPagerImagesBinding
    lateinit var pagerAdapter:ViewPagerAdapter
    lateinit var viewModel:StViewModel
    var sliderDotspanel: LinearLayout? = null
    private var dotscount = 0
    private lateinit var mlist:ArrayList<DataModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        pagerBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_view_pager_images_,
            container,
            false
        )

        doInitialization()
        gettingDataStored()




        return pagerBinding.root
    }

    private fun doInitialization() {
        mlist = ArrayList()
        viewModel  = (activity as MainActivity).viewModel
    }

    private fun gettingDataStored(){
        viewModel.getAllStoredData().observe(viewLifecycleOwner,{data->
            if (data != null){
                mlist.addAll(data)
            }

            Log.d("mData : ", "${mlist.size}")
            pagerAdapter = ViewPagerAdapter(requireContext(), mlist)
            dotscount = pagerAdapter.count
            //Log.d("mData : ", "${mlist.size}")
            pagerBinding.viewPager.apply{
            adapter = pagerAdapter
            }
            pagerAdapter.notifyDataSetChanged()

            if (dotscount >0){
                   setUpDots()
            }
        })
    }


    private fun setUpDots() {
        Log.d("countT: ","$dotscount")
        val dots:Array<ImageView?> = arrayOfNulls(dotscount)

        for (i in 0 until dotscount) {
            dots[i] = ImageView(requireContext())
            dots[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.non_active
                )
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            pagerBinding.SliderDots.addView(dots[i], params)
        }

        dots[0]!!.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.active_dots
            )
        )

        pagerBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                for (i in 0 until dotscount) {
                    dots[i]!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.non_active
                        )
                    )
                }

                dots[position]!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.active_dots
                    )
                )

            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }


}