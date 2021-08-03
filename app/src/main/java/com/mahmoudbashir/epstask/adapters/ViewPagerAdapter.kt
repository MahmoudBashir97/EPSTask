package com.mahmoudbashir.epstask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.mahmoudbashir.epstask.R
import com.mahmoudbashir.epstask.pojo.DataModel
import com.squareup.picasso.Picasso


class ViewPagerAdapter(val context: Context, private val mlist: ArrayList<DataModel>):PagerAdapter() {
    lateinit var layoutInflater: LayoutInflater

    override fun getCount(): Int =mlist.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {


        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = layoutInflater.inflate(R.layout.single_pager_item, null)
        val imageView: ImageView = v.findViewById(R.id.imageView)

        Picasso.get().load(mlist[position].imgUri).into(imageView)


        container.addView(v, 0)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
        container.removeView(o as LinearLayout)
    }
}