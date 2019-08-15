package com.xueqiu.shelf.app.mvp

class TestPresenter : TestMVPContract.BaseTestPresenter() {

    private var count: Int = 0

    override fun initData() {
        count = 0
        view?.showCount(count)
    }

    override fun addOne() {
        count++
        view?.showCount(count)
    }

}