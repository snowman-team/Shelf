package com.xueqiu.shelf.mvvm

import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.xueqiu.shelf.BaseActivity

abstract class MVVMActivity<T : ViewDataBinding, VM : BaseViewModel> : BaseActivity() {

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): VM
    abstract fun getBindingVariable(): Int

    protected val mViewModel by lazy { getViewModel() }
    protected var mViewDataBinding: T? = null

    final override fun setPageView() = setContentView(getLayoutId())

    @CallSuper
    override fun init() {
        mViewDataBinding = DataBindingUtil.setContentView<T>(this, getLayoutId())
        performDataBinding()
        observerDataChanged()
    }

    private fun performDataBinding() {
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.executePendingBindings()
        mViewDataBinding?.lifecycleOwner = this
    }

    open fun observerDataChanged() {}

}