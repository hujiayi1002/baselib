package com.ocse.baseandroid.result

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*
import kotlin.collections.HashMap

/**
 * @author hujiayi
 */
class AvoidOnResultFragment : Fragment() {
    private val mSubjects: MutableMap<Int, PublishSubject<ActivityResultInfo>?> =
        HashMap()
    private val mCallbacks: MutableMap<Int, AvoidOnResult.Callback?> =
        HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun startForResult(intent: Intent?): Observable<ActivityResultInfo> {
        val subject =
            PublishSubject.create<ActivityResultInfo>()
        return subject.doOnSubscribe {
            val requestCode = generateRequestCode()
            mSubjects[requestCode] = subject
            startActivityForResult(intent, requestCode)
        }
    }

    fun startForResult(intent: Intent?, callback: AvoidOnResult.Callback?) {
        val requestCode = generateRequestCode()
        mCallbacks[requestCode] = callback
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        //rxjava方式的处理
        val subject = mSubjects.remove(requestCode)
        if (subject != null) {
            subject.onNext(ActivityResultInfo(resultCode, data!!))
            subject.onComplete()
        }

        //callback方式的处理
        val callback = mCallbacks.remove(requestCode)
        callback?.onActivityResult(resultCode, data)
    }

    private fun generateRequestCode(): Int {
        val random = Random()
        while (true) {
            val code = random.nextInt(65536)
            if (!mSubjects.containsKey(code) && !mCallbacks.containsKey(code)) {
                return code
            }
        }
    }
}