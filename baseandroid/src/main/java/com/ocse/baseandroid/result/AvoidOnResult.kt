package com.ocse.baseandroid.result

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import io.reactivex.Observable

/**
 * @author hujiayi
 */
class AvoidOnResult(activity: FragmentActivity) {
    private val mAvoidOnResultFragment: AvoidOnResultFragment
    private fun getAvoidOnResultFragment(activity: FragmentActivity): AvoidOnResultFragment {
        var avoidOnResultFragment = findAvoidOnResultFragment(activity)
        if (avoidOnResultFragment == null) {
            avoidOnResultFragment = AvoidOnResultFragment()
            val fragmentManager =
                activity.supportFragmentManager
            fragmentManager
                .beginTransaction()
                .add(avoidOnResultFragment, TAG)
                .commitAllowingStateLoss()
            fragmentManager.executePendingTransactions()
        }
        return avoidOnResultFragment
    }

    private fun findAvoidOnResultFragment(activity: FragmentActivity): AvoidOnResultFragment? {
        return activity.supportFragmentManager
            .findFragmentByTag(TAG) as AvoidOnResultFragment?
    }

    fun startForResult(intent: Intent?): Observable<ActivityResultInfo> {
        return mAvoidOnResultFragment.startForResult(intent)
    }

    fun startForResult(clazz: Class<*>?): Observable<ActivityResultInfo> {
        val intent = Intent(mAvoidOnResultFragment.activity, clazz)
        return startForResult(intent)
    }

    fun startForResult(
        intent: Intent?,
        callback: Callback?
    ) {
        mAvoidOnResultFragment.startForResult(intent, callback)
    }

    fun startForResult(
        clazz: Class<*>?,
        callback: Callback?
    ) {
        val intent = Intent(mAvoidOnResultFragment.activity, clazz)
        startForResult(intent, callback)
    }

    interface Callback {
        fun onActivityResult(resultCode: Int, data: Intent?)
    }

    companion object {
        private const val TAG = "AvoidOnResult"
    }

    init {
        mAvoidOnResultFragment = getAvoidOnResultFragment(activity)
    }
}