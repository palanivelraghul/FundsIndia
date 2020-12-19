package com.task.fundsIndia.viewmodel

import androidx.fragment.app.Fragment
import com.task.fundsIndia.R
import com.task.fundsIndia.fragment.HomeListFragment
import com.task.fundsIndia.model.responsemodel.VideoFilesResponseModel
import com.task.fundsIndia.utils.base.AppBaseViewModel

class NavigationActivityViewModel : AppBaseViewModel() {

    private lateinit var callBack: NavigationActivityViewModelCallback

    fun init(callBack: NavigationActivityViewModelCallback) {
        this.callBack = callBack
        callBack.loadFragment(R.id.menu_1, HomeListFragment(callBack), true)
    }

    interface NavigationActivityViewModelCallback {
        fun isInternetConnectionSuccess(): Boolean
        fun showLoader()
        fun dismissLoader()
        fun loadFragment(tabId:Int, fragment: Fragment, isFragmentAddToStack : Boolean)

        fun loadFragmentDetails(videoDetailsModel: VideoFilesResponseModel)
    }

}