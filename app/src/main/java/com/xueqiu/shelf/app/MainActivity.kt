package com.xueqiu.shelf.app

import android.content.Intent
import android.widget.Toast
import com.xueqiu.shelf.BaseActivity
import com.xueqiu.shelf.app.mvp.TestMVPActivity
import com.xueqiu.shelf.app.mvvm.TestMVVMActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun beforeSetView() {
        floatStatusBar()
        floatNavigationBar()
        setStatusBarLightStyle(true)
    }

    override fun setPageView() {
        setContentView(R.layout.activity_main)
    }

    override fun beforeInit() {
        // do something
    }

    override fun init() {
        btn_mvvm.setOnClickListener {
            startActivity(Intent(this, TestMVVMActivity::class.java))
        }

        btn_mvp.setOnClickListener {
            startActivity(Intent(this, TestMVPActivity::class.java))
        }
        Observable.fromCallable {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
        }.subscribe {
            // do something
        }.autoDispose()
    }

}
