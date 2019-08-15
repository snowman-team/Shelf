package com.xueqiu.shelf.app.mvp

import android.widget.Toast
import com.xueqiu.shelf.app.R
import com.xueqiu.shelf.mvp.MVPActivity
import kotlinx.android.synthetic.main.activit_test_mvp.*

class TestMVPActivity : MVPActivity<TestMVPContract.BaseTestPresenter, TestMVPContract.ITestViewer>(),
    TestMVPContract.ITestViewer {


    override fun createPresenter(): TestMVPContract.BaseTestPresenter = TestPresenter()

    override fun getLayoutId(): Int = R.layout.activit_test_mvvm

    override fun init() {
        btn_add.setOnClickListener {
            presenter.addOne()
        }
        presenter.initData()
    }

    override fun showCount(count: Int) {
        tv_count.text = count.toString()
        Toast.makeText(this, "new count -> $count", Toast.LENGTH_SHORT).show()
    }
}