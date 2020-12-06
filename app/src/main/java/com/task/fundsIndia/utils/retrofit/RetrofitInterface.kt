package com.task.fundsIndia.utils.retrofit

import com.task.fundsIndia.model.responsemodel.VideoFilesResponseModel
import com.task.fundsIndia.utils.AppConstant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

interface RetrofitInterface {
    @GET(ServicePathConstant.GET_VIDEO_FILES)
    suspend fun getVideoFiles( @HeaderMap headerMap: Map<String, String>, @Query(AppConstant.API_OFFSET) offset: String, @Query(AppConstant.API_LIMIT) limit: String ): Response<MutableList<VideoFilesResponseModel>>?

}