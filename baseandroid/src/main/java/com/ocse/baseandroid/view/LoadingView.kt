package com.ocse.baseandroid.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.ocse.baseandroid.R

class LoadingView(context: Activity) :
    Dialog(context, R.style.dialog) {
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        val view =
            LayoutInflater.from(context).inflate(R.layout.loading_view, null)
        setContentView(view)
        val imageView =
            view.findViewById<ImageView>(R.id.image)
        val a =
            AnimationUtils.loadAnimation(context, R.anim.rotate)
        val lin = LinearInterpolator()
        a.interpolator = lin
        imageView.startAnimation(a)
        //        setCancelable(false);
        setCanceledOnTouchOutside(false)
    }

    fun setCancelable() {
        setCancelable(true)
        setCanceledOnTouchOutside(true)
    }

}