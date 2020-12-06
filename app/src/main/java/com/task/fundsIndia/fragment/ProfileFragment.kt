package com.task.fundsIndia.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.task.fundsIndia.databinding.FragmentProfileBinding
import com.task.fundsIndia.utils.NetworkUtils
import com.task.fundsIndia.utils.base.BaseFragmentViewModel
import com.task.fundsIndia.viewmodel.ProfileFragmentViewModel

class ProfileFragment : BaseFragmentViewModel<ProfileFragmentViewModel>(),
    ProfileFragmentViewModel.ProfileFragmentViewModelCallBack {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.viewModel = onCreateViewModel()
        return binding.root
    }

    override fun onCreateViewModel(): ProfileFragmentViewModel {
        viewModel = ViewModelProvider(this).get(ProfileFragmentViewModel::class.java)
        binding.wbPage.loadUrl("https://www.fundsindia.com/")
        return viewModel
    }

    override fun isInternetConnectionSuccess(): Boolean {
        return NetworkUtils.isConnected(activity!!)
    }

    override fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
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

}
