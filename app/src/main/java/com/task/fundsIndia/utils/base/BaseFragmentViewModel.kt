package com.task.fundsIndia.utils.base

import android.app.Dialog
import android.content.Context
import androidx.fragment.app.Fragment
import com.task.fundsIndia.R

abstract class BaseFragmentViewModel<VM : AppBaseViewModel> : Fragment() {

    abstract fun onCreateViewModel(): VM
    private var mDialog: Dialog? = null

    open fun <T : AppBaseViewModel?> onRetry(viewModel: T) {
        //findViewById(R.id.btn_api_retry).setOnClickListener(View.OnClickListener { viewModel?.onRetryClickListener() })
        //findViewById(R.id.btn_network_reload).setOnClickListener(View.OnClickListener { viewModel?.onRetryClickListener() })
    }

    fun showProgressBar(context: Context) {
        try {
            if (mDialog == null) {
                mDialog = Dialog(context, R.style.CustomProgressTheme)
                mDialog?.setContentView(R.layout.custom_progress)
                mDialog?.setCancelable(false)
                mDialog?.setCanceledOnTouchOutside(false)
                mDialog?.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismissProgress() {
        try {
            if (mDialog != null && mDialog!!.isShowing) {
                mDialog?.dismiss()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        mDialog = null
    }
}