package com.task.fundsIndia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.fundsIndia.R
import com.task.fundsIndia.databinding.ItemListAdapterBinding
import com.task.fundsIndia.model.responsemodel.VideoFilesResponseModel
import com.task.fundsIndia.utils.CommonUtils

class VideoFileListAdapter(private val callBack: VideoFileListAdapterCallBack, private var mVideoFileList: MutableList<VideoFilesResponseModel>) :
    RecyclerView.Adapter<VideoFileListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(mVideoFileList[position], callBack)

    }

    override fun getItemCount(): Int {
        return mVideoFileList.size
    }

    fun updateData(videoFilesList: List<VideoFilesResponseModel>) {
        val size = mVideoFileList.size
        mVideoFileList.addAll(videoFilesList)
        notifyItemRangeInserted(size, videoFilesList.size)
    }

    class ViewHolder(var mBinding: ItemListAdapterBinding) : RecyclerView.ViewHolder(mBinding.root) {
        fun bindData(videoFile: VideoFilesResponseModel, mCallBack: VideoFileListAdapterCallBack) {
            mBinding.callBack = mCallBack
            mBinding.detailsModel = videoFile
            mBinding.tvDate.text = String.format(mBinding.tvDate.context.getString(R.string.text_video_Id), videoFile.id.toString())
            CommonUtils.loadUrlImage(mBinding.imgVideoThumbnail.context, mBinding.imgVideoThumbnail, videoFile.thumbnail)
        }
    }

    interface VideoFileListAdapterCallBack {
        fun onItemClickCallBack(videoFilesResponseModel: VideoFilesResponseModel)
    }

}
