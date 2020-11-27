package com.ocse.baseandroid.utils

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.ocse.baseandroid.R
import com.ocse.baseandroid.view.LoadingView
import com.tbruyelle.rxpermissions2.RxPermissions
import com.tencent.smtt.sdk.TbsReaderView
import kotlinx.android.synthetic.main.activity_word_view_acivity.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class WordViewAcivity : AppCompatActivity(), TbsReaderView.ReaderCallback {
    private lateinit var url: String
    private lateinit var fileName: String
    private lateinit var loadingView: LoadingView
    private var mTbsReaderView: TbsReaderView? = null

    companion object {
        const val FILENAME = "fileName"
        const val URL = "url"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_view_acivity)
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init()
        fileName = intent.getStringExtra(FILENAME)
        url = intent.getStringExtra(URL)
        mTbsReaderView = TbsReaderView(this@WordViewAcivity, this)
        tvTitle.text = "$fileName"
        relBack.setOnClickListener { finish() }
        getPermission()
        initView()

    }

    private fun initView() {
        loadingView = LoadingView(this)
        loadingView.setCanceledOnTouchOutside(true)
        if (!loadingView.isShowing) {
            loadingView.show()
        }
        wordView.addView(
            mTbsReaderView,
            RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
        downLoadFile()

    }

    @SuppressLint("CheckResult")
    private fun getPermission() {
        val rxPermission = RxPermissions(this)
        rxPermission.request(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).subscribe { aBoolean ->
            if (aBoolean) {
            }
        }
    }

    private fun downLoadFile() {
        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + SharePerferenceUtil.getString("token"))
                .addHeader("csrf-csrf", "csrf-csrf")
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        loadingView.dismiss()
                        // 下载失败
                        Toast.makeText(this@WordViewAcivity, "文件下载失败!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                @Throws(IOException::class)
                override fun onResponse(
                    call: Call,
                    response: Response
                ) {
                    var `is`: InputStream? = null
                    val buf = ByteArray(2048)
                    var len = 0
                    var fos: FileOutputStream? = null
                    // 储存下载文件的目录
                    val savePath: String? = ObtainApplication.getApp()?.filesDir?.absolutePath
                    try {
                        `is` = response.body!!.byteStream()
                        if (!File(savePath).exists()) {
                            File(savePath).mkdirs()
                        }
                        val file = File(savePath, fileName)
                        if (file.exists()) {
                            file.delete()
                        }
                        fos = FileOutputStream(file)
                        while (`is`.read(buf).also { len = it } != -1) {
                            fos.write(buf, 0, len)
                        }
                        fos.flush()
                        // 下载完成
                        val message = Message()
                        message.what = 6
                        message.obj = file
                        mHandler.sendMessage(message)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        try {
                            `is`?.close()
                            fos?.close()
                        } catch (e: IOException) {
                        }
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                6 -> {
                    loadingView.dismiss()
                    displayFile(msg.obj as File)
                }
            }
            super.handleMessage(msg)
        }
    }


    private fun displayFile(file: File) {
        val bundle = Bundle()
        val path = file.absolutePath
        val name = path.substring(path.lastIndexOf(".") + 1)
        val tempPath = path.substring(0, path.lastIndexOf("/")) + "/temp." + name
        val renameFile = File(tempPath)
        if (renameFile.exists()) {
            renameFile.deleteOnExit()
        }
        file.renameTo(renameFile)
        val o = renameFile.exists()
        val l = renameFile.length()
        bundle.putString("filePath", renameFile.path)
        bundle.putString("tempPath", filesDir.path)

        val result =
            mTbsReaderView!!.preOpen(parseFormat(renameFile.name), false)
        if (result) {
            mTbsReaderView!!.openFile(bundle)
        } else {
            tvNoType.visibility = View.VISIBLE
//            QbSdk.openFileReader(this,renameFile.path,null
//            ) { p0 -> Log.e("TAG", "onReceiveValue: $p0" ) }
        }
    }

    private fun parseFormat(fileName: String): String? {
        return fileName.substring(fileName.lastIndexOf(".") + 1)
    }

    override fun onCallBackAction(p0: Int?, p1: Any?, p2: Any?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        mTbsReaderView!!.onStop()
    }

}