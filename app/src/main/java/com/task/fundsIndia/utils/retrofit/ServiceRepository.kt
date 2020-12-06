package com.task.fundsIndia.utils.retrofit

import com.task.fundsIndia.utils.base.AppBaseViewModel

object ServiceRepository {

    private const val BASE_URL = "https://qgkpjarwfl.execute-api.us-east-1.amazonaws.com/dev/"

    const val GET_VIDEO_FILES = 1000001

    suspend fun getVideoFiles(viewModel: AppBaseViewModel, headerMap: Map<String, String>, offset: String, limit: String) {
        val apiClient = APIClient.getClient(BASE_URL)?.create(RetrofitInterface::class.java)
        val response = apiClient?.getVideoFiles(headerMap, offset, limit)
        if (response?.isSuccessful!! && response.body() != null) {
            viewModel.onAPISuccess(response.body()!!, GET_VIDEO_FILES)
        } else {
            viewModel.onAPIFailure(null, GET_VIDEO_FILES)
        }
    }


}