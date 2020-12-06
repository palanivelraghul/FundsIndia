package com.task.fundsIndia.utils.base

abstract class BaseActivityViewModel<VM : AppBaseViewModel> : BaseAppCompactActivity() {

    abstract fun onCreateViewModel(): VM

    open fun <T : AppBaseViewModel?> onRetry(viewModel: T) {
        //findViewById(R.id.btn_api_retry).setOnClickListener(View.OnClickListener { viewModel?.onRetryClickListener() })
        //findViewById(R.id.btn_network_reload).setOnClickListener(View.OnClickListener { viewModel?.onRetryClickListener() })
    }
}