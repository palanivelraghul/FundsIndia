package com.task.fundsIndia.utils.base

import androidx.lifecycle.ViewModel
import com.task.fundsIndia.model.responsemodel.VideoFilesResponseModel

abstract class AppBaseViewModel : ViewModel(), APICallBack {

    open fun onRetryClickListener() {
        // Todo Override this method where you want retry.
    }

    override fun onAPIFailure(failureResponse: String?, failureTransactionId: Int) {
        super.onAPIFailure(failureResponse, failureTransactionId)
    }

    override fun onAPISuccess(successResponse: MutableList<VideoFilesResponseModel>, successTransactionId: Int) {
        super.onAPISuccess(successResponse, successTransactionId)
    }

    override fun initiateAPICall(apiTransactionId: Int) {
        super.initiateAPICall(apiTransactionId)
    }


}