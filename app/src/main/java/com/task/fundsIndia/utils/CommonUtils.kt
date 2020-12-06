package com.task.fundsIndia.utils

import android.content.Context
import android.widget.ImageView
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.task.fundsIndia.R


object CommonUtils {

    fun loadUrlImage(context: Context?, imageView: ImageView, imageUrl: String?) {
        Glide.with(context!!).load(imageUrl).thumbnail(0.5f)
            .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_status_none)
            .error(Glide.with(imageView).load(R.drawable.ic_status_none)).into(imageView)
    }

    fun setSnapHelperProperties(recyclerView: RecyclerView) {
        val snapHelper: SnapHelper = PagerSnapHelper()
        recyclerView.onFlingListener = null
        snapHelper.attachToRecyclerView(recyclerView)
    }

}