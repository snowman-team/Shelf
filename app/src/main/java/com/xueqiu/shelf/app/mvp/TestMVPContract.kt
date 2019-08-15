package com.xueqiu.shelf.app.mvp

import com.xueqiu.shelf.mvp.BasePresenter
import com.xueqiu.shelf.mvp.IViewer

interface TestMVPContract {

    interface ITestViewer : IViewer {
        fun showCount(count: Int)
    }

    abstract class BaseTestPresenter : BasePresenter<ITestViewer>() {
        abstract fun initData()
        abstract fun addOne()
    }

}