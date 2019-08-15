package com.xueqiu.shelf.mvp

interface IPresenter<V : IViewer> {
    fun attachView(view: V)
    fun detachView(retainInstance: Boolean)
}