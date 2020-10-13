package com.ocse.baseandroid.utils

import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ocse.baseandroid.R
import com.ocse.baseandroid.view.LoadingView
import com.tencent.smtt.sdk.TbsReaderView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_view_acivity)
        fileName = intent.getStringExtra("fileName")
        url = intent.getStringExtra("url")
        mTbsReaderView = TbsReaderView(this@WordViewAcivity, this)
        loading()
        downLoadFile()
    }

    private fun loading() {
        if (!loadingView.isShowing) {
            loadingView.show()
        }
    }

    private  fun downLoadFile() {
        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
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
                    val savePath: String = cacheDir.path
                    try {
                        `is` = response.body!!.byteStream()
                        val file = File(savePath, fileName)
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
                        } catch (e: IOException) {
                        }
                        try {
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
        val renameFile = File(fileName)
        if (renameFile.exists()) {
            renameFile.deleteOnExit()
        }
        file.renameTo(renameFile)
        val o = renameFile.exists()
        val l = renameFile.length()
        bundle.putString("filePath", renameFile.path)
        bundle.putString("tempPath",cacheDir.path)
        val result: Boolean =
            mTbsReaderView!!.preOpen(renameFile.path, false);
        if (result) {
            mTbsReaderView?.openFile(bundle)
        }
    }

    override fun onCallBackAction(p0: Int?, p1: Any?, p2: Any?) {

    }

}