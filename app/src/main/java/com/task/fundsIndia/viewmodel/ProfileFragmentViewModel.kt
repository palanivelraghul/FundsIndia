package com.task.fundsIndia.viewmodel

import com.task.fundsIndia.utils.base.AppBaseViewModel

class ProfileFragmentViewModel : AppBaseViewModel() {

    interface ProfileFragmentViewModelCallBack {
        fun showToastMessage(message: String)
        fun isInternetConnectionSuccess(): Boolean
        fun showLoader()
        fun dismissLoader()
        fun showNetworkError()
        fun showAPIError()
    }

}
