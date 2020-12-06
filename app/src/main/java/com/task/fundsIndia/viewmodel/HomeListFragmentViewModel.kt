package com.task.fundsIndia.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.fundsIndia.model.responsemodel.VideoFilesResponseModel
import com.task.fundsIndia.utils.base.AppBaseViewModel
import com.task.fundsIndia.utils.retrofit.ServiceRepository
import kotlinx.coroutines.launch

class HomeListFragmentViewModel : AppBaseViewModel() {

    private var retryTransactionId: Int = 0
    val mVideoListLiveData = MutableLiveData<MutableList<VideoFilesResponseModel>>()
    private var limit = 10
    private var offset = 0
    private val headerKey = "x-api-key"
    private val headerValue = "jvmNAyPNr1JhiCeUlYmB2ae517p3Th0aGG6syqMb"

    private lateinit var mCallBack: HomeListFragmentViewModelCallBack

    fun init(callBackList: HomeListFragmentViewModelCallBack) {
        this.mCallBack = callBackList;
        mCallBack.showLoader()
        initiateAPICall(ServiceRepository.GET_VIDEO_FILES)
    }

    override fun initiateAPICall(apiTransactionId: Int) {
        super.initiateAPICall(apiTransactionId)
        if (mCallBack.isInternetConnectionSuccess()) {
            viewModelScope.launch {
                when (apiTransactionId) {
                    ServiceRepository.GET_VIDEO_FILES -> ServiceRepository.getVideoFiles(
                        this@HomeListFragmentViewModel,
                        getHeaderMap(),
                        offset.toString(),
                        limit.toString()
                    )
                }
            }
        } else {
            mCallBack.showNetworkError()
        }
    }

    private fun getHeaderMap(): Map<String, String> {
        var headerMap = mutableMapOf<String, String>()
        headerMap[headerKey] = headerValue
        return headerMap
    }

    override fun onRetryClickListener() {
        super.onRetryClickListener()
        initiateAPICall(retryTransactionId)
    }

    override fun onAPISuccess(successResponse: MutableList<VideoFilesResponseModel>, successTransactionId: Int) {
        super.onAPISuccess(successResponse, successTransactionId)
        mVideoListLiveData.value = successResponse
    }

    override fun onAPIFailure(failureResponse: String?, failureTransactionId: Int) {
        super.onAPIFailure(failureResponse, failureTransactionId)
        retryTransactionId = failureTransactionId
        mCallBack.showAPIError()
        mCallBack.dismissLoader()
    }

    fun initiateVideoList(videoFilesList: MutableList<VideoFilesResponseModel>) {
        mCallBack.loadVideoListAdapter(videoFilesList)
        mCallBack.dismissLoader()
    }

    fun getRecyclerViewPagination(layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener? {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + firstVisibleItem >= totalItemCount && firstVisibleItem >= 0 && totalItemCount >= limit) {
                    offset += if(offset > 0){
                        10
                    }else{
                        limit
                    }
                    mCallBack.showLazyLoader()
                    initiateAPICall(ServiceRepository.GET_VIDEO_FILES)
                }
            }
        }
    }

    interface HomeListFragmentViewModelCallBack {
        fun showToastMessage(message: String)
        fun isInternetConnectionSuccess(): Boolean
        fun showLoader()
        fun dismissLoader()
        fun showNetworkError()
        fun showAPIError()
        fun showLazyLoader()
        fun hideLazyLoader()
        fun loadVideoListAdapter(videoFilesList: MutableList<VideoFilesResponseModel>)
    }

}
