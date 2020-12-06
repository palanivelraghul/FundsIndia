package com.task.fundsIndia.activity

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.task.fundsIndia.R
import com.task.fundsIndia.databinding.ActivityNaigationBinding
import com.task.fundsIndia.fragment.HomeListFragment
import com.task.fundsIndia.fragment.ProfileFragment
import com.task.fundsIndia.fragment.VideoDetailsFragment
import com.task.fundsIndia.model.responsemodel.VideoFilesResponseModel
import com.task.fundsIndia.utils.NetworkUtils
import com.task.fundsIndia.utils.base.BaseActivityViewModel
import com.task.fundsIndia.viewmodel.NavigationActivityViewModel

class NavigationActivity : BaseActivityViewModel<NavigationActivityViewModel>(),
    NavigationActivityViewModel.NavigationActivityViewModelCallback {

    private var isExitApp = false

    private lateinit var binding: ActivityNaigationBinding
    private lateinit var viewModel: NavigationActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaigationBinding.inflate(layoutInflater)
        binding.viewmodel = onCreateViewModel()
        setContentView(binding.root)
    }

    override fun onCreateViewModel(): NavigationActivityViewModel {
        viewModel = ViewModelProvider(this).get(NavigationActivityViewModel::class.java)
        viewModel.init(this)
        initiateNavigationListener()
        onRetry(viewModel)
        return viewModel
    }

    private fun initiateNavigationListener() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_1 -> {
                    loadFragment(HomeListFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_2 -> {
                    loadFragment(ProfileFragment())
                    binding.toolBar.title = getString(R.string.text_funds_india)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    override fun loadFragment(fragment: Fragment) {
        if (!isSimilarFragment(fragment)) {
            setTootlTitle(fragment)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fl_container, fragment, fragment.javaClass.simpleName)
            transaction.addToBackStack(fragment.javaClass.simpleName)
            transaction.commit()
        }
    }

    private fun setTootlTitle(fragment: Fragment) {
        binding.toolBar.title = when {
            HomeListFragment(this).javaClass.simpleName == fragment.javaClass.simpleName -> {
                getString(R.string.text_video)
            }
            ProfileFragment().javaClass.simpleName == fragment.javaClass.simpleName -> {
                getString(R.string.text_funds_india)
            }
            else -> {
                getString(R.string.text_video_details)
            }
        }
    }

    override fun loadFragmentDetails(videoFilesResponseModel: VideoFilesResponseModel) {
        loadFragment(VideoDetailsFragment(videoFilesResponseModel.thumbnail!!))
    }

    override fun isInternetConnectionSuccess(): Boolean {
        return NetworkUtils.isConnected(this)

    }

    override fun showLoader() {
        showProgressBar(this)
    }

    override fun dismissLoader() {
        dismissProgress()
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (getActiveFragment() != null) {
             if (HomeListFragment(this).javaClass.simpleName == getActiveFragment()!!.javaClass.simpleName) {
                if (!isExitApp) {
                    isExitApp = true
                    Toast.makeText(this, "Press once again to exit", Toast.LENGTH_SHORT).show()
                } else {
                    finish()
                }
            } else {
                binding.toolBar.title = getString(R.string.text_video)
                binding.bottomNavigation.menu.getItem(0).isChecked = true
                val fm: FragmentManager = this.supportFragmentManager
                fm.popBackStack()
            }
        } else {
            finish()
        }
    }


}