package com.task.fundsIndia.utils.base

import com.task.fundsIndia.model.responsemodel.VideoFilesResponseModel

interface APICallBack {
    fun initiateAPICall(apiTransactionId: Int) {}

    fun onAPISuccess(successResponse: MutableList<VideoFilesResponseModel>, successTransactionId: Int) {}

    fun onAPIFailure(failureResponse: String?, failureTransactionId: Int) {}
}