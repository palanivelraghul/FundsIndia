package com.task.fundsIndia.utils.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.task.fundsIndia.R


open class BaseAppCompactActivity : AppCompatActivity() {

    private var mDialog: Dialog? = null

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

    open fun isSimilarFragment(fragment: Fragment?): Boolean {
        return fragment != null && getActiveFragment() != null && fragment.javaClass.name == getActiveFragment()!!.javaClass.name
    }

    open fun getActiveFragment(): Fragment? {
        if (supportFragmentManager.backStackEntryCount == 0) {
            return null
        }
        val tag =
            supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name
        return supportFragmentManager.findFragmentByTag(tag)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected open fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    open fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransitionExit();
    }
}