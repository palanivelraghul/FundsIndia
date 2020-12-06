package com.task.fundsIndia.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.task.fundsIndia.databinding.FragmentVideoDetailsBinding
import com.task.fundsIndia.utils.CommonUtils

class VideoDetailsFragment(private var imageUrl: String) : Fragment() {

    private lateinit var binding: FragmentVideoDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentVideoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CommonUtils.loadUrlImage(activity, binding.imgImage, imageUrl)
    }


}
