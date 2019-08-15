package com.xueqiu.shelf.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.xueqiu.shelf.BaseFragment

abstract class MVPFragment<out P : BasePresenter<V>, V : IViewer> : BaseFragment() {

    val presenter: P by lazy { createPresenter() }

    @LayoutRes
    abstract fun getLayoutId(): Int

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (this !is IViewer) {
            throw NotImplementedError("Viewer not implemented")
        }
    }

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    @CallSuper
    @Suppress("UNCHECKED_CAST")
    override fun beforeInit() {
        presenter.attachView(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView(retainInstance)
    }

    protected abstract fun createPresenter(): P

    override fun onVisible() {
        super.onVisible()
        presenter.onShow()
    }

    override fun onInvisible() {
        super.onInvisible()
        presenter.onHidden()
    }
}


