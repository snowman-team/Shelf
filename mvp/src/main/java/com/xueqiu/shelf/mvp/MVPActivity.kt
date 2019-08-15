package com.xueqiu.shelf.mvp

import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.xueqiu.shelf.BaseActivity

abstract class MVPActivity<out P : BasePresenter<V>, V : IViewer> : BaseActivity() {

    private val mPresenter: P by lazy { createPresenter() }
    val presenter = mPresenter

    @LayoutRes
    abstract fun getLayoutId(): Int

    @CallSuper
    override fun beforeInit() {
        if (this !is IViewer) {
            throw NotImplementedError("Viewer not implemented")
        }
        @Suppress("UNCHECKED_CAST")
        presenter.attachView(this as V)
    }

    final override fun setPageView() = setContentView(getLayoutId())

    @CallSuper
    override fun onDestroy() {
        presenter.detachView(false)
        super.onDestroy()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        presenter.onShow()
    }

    @CallSuper
    override fun onPause() {
        presenter.onHidden()
        super.onPause()
    }

    protected abstract fun createPresenter(): P

}