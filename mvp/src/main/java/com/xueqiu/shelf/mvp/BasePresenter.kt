package com.xueqiu.shelf.mvp

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

abstract class BasePresenter<V : IViewer> : IPresenter<V> {

    private var mViewRef: WeakReference<V>? = null

    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    protected var isActive = false

    protected fun addDispose(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    protected fun dispose() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    var view: V? = null
        get() = mViewRef?.get()
        private set

    @CallSuper
    override fun attachView(view: V) {
        mViewRef = WeakReference(view)
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable = CompositeDisposable()
        }
    }

    @CallSuper
    override fun detachView(retainInstance: Boolean) {
        dispose()
        mViewRef?.clear()
        mViewRef = null
    }

    fun isViewAttached(): Boolean = mViewRef?.get() != null

    open fun onShow() {
        isActive = true
    }

    open fun onHidden() {
        isActive = false
    }

    fun Disposable?.autoDispose() {
        this?.let { addDispose(it) }
    }
}