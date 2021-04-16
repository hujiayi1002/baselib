package com.ocse.androidbaselib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ImmersionBar
import com.ocse.androidbaselib.databinding.ActivityMain2Binding
import com.ocse.baseandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity(R.layout.activity_main2) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

    }

     private fun initView() {
        titleBar.setOnLeftClick(View.OnClickListener {
            finish()
        })
    }

}