package com.ocse.baseandroid.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideManager {
    companion object{
        fun load(obj:Any,img:ImageView){
            Glide.with(ObtainApplication.getApp()!!).load(obj).into(img)
        }
        fun load(obj:Any,img:ImageView,placeholder:Int){
            Glide.with(ObtainApplication.getApp()!!).load(obj).placeholder(placeholder).into(img)
        }
    }
}