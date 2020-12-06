package com.task.fundsIndia.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.fundsIndia.adapter.VideoFileListAdapter
import com.task.fundsIndia.databinding.FragmentHomeListBinding
import com.task.fundsIndia.model.responsemodel.VideoFilesResponseModel
import com.task.fundsIndia.utils.NetworkUtils
import com.task.fundsIndia.utils.base.BaseFragmentViewModel
import com.task.fundsIndia.viewmodel.HomeListFragmentViewModel
import com.task.fundsIndia.viewmodel.NavigationActivityViewModel

class HomeListFragment(private var mCallBack: NavigationActivityViewModel.NavigationActivityViewModelCallback) :
    BaseFragmentViewModel<HomeListFragmentViewModel>(), HomeListFragmentViewModel.HomeListFragmentViewModelCallBack,
    VideoFileListAdapter.VideoFileListAdapterCallBack {

    private lateinit var mBinding: FragmentHomeListBinding
    private lateinit var mViewModel: HomeListFragmentViewModel

    private var adapter: VideoFileListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentHomeListBinding.inflate(inflater, container, false)
        mBinding.viewModel = onCreateViewModel()
        return mBinding.root
    }

    override fun onCreateViewModel(): HomeListFragmentViewModel {
        mViewModel = ViewModelProvider(this).get(HomeListFragmentViewModel::class.java)
        mViewModel.init(this)
        mViewModel.mVideoListLiveData.observe(this, VideoListResponseObserver())
        return mViewModel
    }

    override fun isInternetConnectionSuccess(): Boolean {
        return NetworkUtils.isConnected(activity!!)
    }

    override fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLazyLoader() {
        mBinding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLazyLoader() {
        mBinding.progressBar.visibility = View.GONE
    }

    override fun showLoader() {
        showProgressBar(activity!!)
    }

    override fun dismissLoader() {
        dismissProgress()
    }

    override fun showNetworkError() {
        TODO("Not yet implemented")
    }

    override fun showAPIError() {
        TODO("Not yet implemented")
    }

    override fun loadVideoListAdapter(videoFilesList: MutableList<VideoFilesResponseModel>) {
        if (adapter != null) {
            adapter!!.updateData(videoFilesList);
        } else {
            adapter = VideoFileListAdapter(this, videoFilesList)
            mBinding.rvPassengerList.adapter = adapter
            mBinding.rvPassengerList.layoutManager = LinearLayoutManager(activity!!)
            mBinding.rvPassengerList.addOnScrollListener(mViewModel.getRecyclerViewPagination(mBinding.rvPassengerList.layoutManager as LinearLayoutManager)!!)
        }
    }

    override fun onItemClickCallBack(videoDetailsModel: VideoFilesResponseModel) {
        mCallBack.loadFragmentDetails(videoDetailsModel)
    }

    private inner class VideoListResponseObserver : Observer<MutableList<VideoFilesResponseModel>> {
        override fun onChanged(videoFilesList: MutableList<VideoFilesResponseModel>) {
            hideLazyLoader()
            mViewModel.initiateVideoList(videoFilesList)
            mBinding.executePendingBindings()
        }
    }
}
