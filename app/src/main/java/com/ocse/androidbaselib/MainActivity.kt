package com.ocse.androidbaselib

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.ocse.androidbaselib.bean.UserBean
import com.ocse.androidbaselib.databinding.ActivityMainBinding
import com.ocse.androidbaselib.model.BaseModel
import com.ocse.baseandroid.base.BaseActivity
import com.ocse.baseandroid.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var vm: BaseModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPermission()
        vm = get(BaseModel::class.java)
        vm.userMutableLiveData.observe(this,
            Observer<UserBean> {
                log("ActivityA中接收user：${it.access_token}");
                dataBinding?.user = it
            })

        text2.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                ToastUtil.show("开始搜索")
                true
            }
            false
        }
        val sd = ArrayList<String>()
        repeat(sd.size) {  }
        textView.setOnClickListener {
            vm.getUser()
//            val intent = Intent(this, MainActivity2::class.java)
//            // create the transition animation - the images in the layouts
//            // of both activities are defined with android:transitionName="robot"
//            val options = ActivityOptions
//                .makeSceneTransitionAnimation(this, img, "robot")
//            // start the new activity
//            startActivity(intent, options.toBundle())
        }
        button.setOnClickListener {
            ToastUtil.show("1234")
//            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            //Intent intent=new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
            //Intent intent=new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
//            startActivityForResult(intent, 0)
        }
    }

    fun haspre(x: Any) = when (x) {
        is String -> {
           x.subSequence(0,10)
        }
        else -> false

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        Log.i("zpf", "" + results?.get(0).toString())
        textView.text = results?.get(0).toString()
    }

    // 2.更改数据
    private fun getUser() {
//        val user = UserBean();
        //同步更改setValue  ;  异步更改postValue
//        get(BaseModel::class.java).userMutableLiveData.postValue(user)
    }

    override fun initView() {

    }

    override fun initData() {

    }
}