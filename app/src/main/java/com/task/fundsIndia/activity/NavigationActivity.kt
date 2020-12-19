package com.task.fundsIndia.activity

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
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
import java.util.*
import kotlin.collections.HashMap

class NavigationActivity : BaseActivityViewModel<NavigationActivityViewModel>(),
    NavigationActivityViewModel.NavigationActivityViewModelCallback {

    private var isExitApp = false

    private lateinit var binding: ActivityNaigationBinding
    private lateinit var viewModel: NavigationActivityViewModel
    private var backStackFragmentMapping = HashMap<Int, Stack<Fragment>>()
    private var currentTabId  = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaigationBinding.inflate(layoutInflater)
        binding.viewmodel = onCreateViewModel()
        setContentView(binding.root)
    }

    override fun onCreateViewModel(): NavigationActivityViewModel {
        viewModel = ViewModelProvider(this).get(NavigationActivityViewModel::class.java)
        initializeFragmentMappingStack()
        viewModel.init(this)
        initiateNavigationListener()
        onRetry(viewModel)
        return viewModel
    }

    private fun initializeFragmentMappingStack() {
        backStackFragmentMapping[R.id.menu_1] = Stack<Fragment>()
        backStackFragmentMapping[R.id.menu_2] = Stack<Fragment>()
    }

    private fun initiateNavigationListener() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            if (backStackFragmentMapping[it.itemId]!!.size == 0) {
                when (it.itemId) {
                    R.id.menu_1 -> {
                        val fragment = HomeListFragment(this)
                        loadFragment(R.id.menu_1, fragment, true)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.menu_2 -> {
                        loadFragment(R.id.menu_2, ProfileFragment(), true)
                        binding.toolBar.title = getString(R.string.text_funds_india)
                        return@setOnNavigationItemSelectedListener true
                    }
                }
            } else {
                loadFragment(it.itemId, backStackFragmentMapping[it.itemId]!!.lastElement(), false)
            }
            false
        }
    }

    override fun loadFragment(tabId: Int, fragment: Fragment, isFragmentAddToStack: Boolean) {
        currentTabId = tabId
        if (!isSimilarFragment(fragment)) {
            setTootlTitle(fragment)
            if (isFragmentAddToStack) {
                backStackFragmentMapping[tabId]!!.push(fragment)
            }
            val transaction = supportFragmentManager.beginTransaction()
            if (!fragment.isAdded) {
                transaction.add(R.id.fl_container, fragment, fragment.javaClass.simpleName)
            } else {
                transaction.replace(R.id.fl_container, fragment, fragment.javaClass.simpleName)
            }
            transaction.commit()
        }
    }

    private fun setTootlTitle(fragment: Fragment) {
        binding.toolBar.title = when {
            HomeListFragment(this).javaClass.simpleName == fragment.javaClass.simpleName -> {
                binding.bottomNavigation.menu[0].isChecked = true
                getString(R.string.text_video)
            }
            ProfileFragment().javaClass.simpleName == fragment.javaClass.simpleName -> {
                binding.bottomNavigation.menu[1].isChecked = true
                getString(R.string.text_funds_india)
            }
            else -> {
                binding.bottomNavigation.menu[0].isChecked = true
                getString(R.string.text_video_details)
            }
        }
    }

    override fun loadFragmentDetails(videoFilesResponseModel: VideoFilesResponseModel) {
        if (backStackFragmentMapping[R.id.menu_1]!!.size == 2) {
            removeFragment(R.id.menu_1)
        }
        loadFragment(R.id.menu_1, VideoDetailsFragment(videoFilesResponseModel.thumbnail!!), true)
    }

    private fun removeFragment(tabId: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.remove(backStackFragmentMapping[tabId]!!.lastElement())
        transaction.commit()
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
        if (currentTabId == R.id.menu_1) {
            if (backStackFragmentMapping[R.id.menu_1]!!.size == 2) {
                removeFragment(R.id.menu_1)
                backStackFragmentMapping[R.id.menu_1]!!.pop()
                loadFragment(R.id.menu_1, backStackFragmentMapping[R.id.menu_1]!!.lastElement(), false)
            } else {
                if (!isExitApp) {
                    isExitApp = true
                    showToastMessage("Press once again to exit")
                } else {
                    finish()
                }
            }
        } else {
            removeFragment(R.id.menu_2)
            loadFragment(R.id.menu_1, backStackFragmentMapping[R.id.menu_1]!!.lastElement(), false)
        }
    }


}