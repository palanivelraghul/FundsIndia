package com.task.fundsIndia.viewmodel

import androidx.fragment.app.Fragment
import com.task.fundsIndia.fragment.HomeListFragment
import com.task.fundsIndia.model.responsemodel.VideoFilesResponseModel
import com.task.fundsIndia.utils.base.AppBaseViewModel

class NavigationActivityViewModel : AppBaseViewModel() {

    private lateinit var callBack: NavigationActivityViewModelCallback

    fun init(callBack: NavigationActivityViewModelCallback) {
        this.callBack = callBack
        callBack.loadFragment(HomeListFragment(callBack))
    }

    interface NavigationActivityViewModelCallback {
        fun isInternetConnectionSuccess(): Boolean
        fun showLoader()
        fun dismissLoader()
        fun loadFragment(fragment: Fragment)

        fun loadFragmentDetails(videoDetailsModel: VideoFilesResponseModel)
    }

}