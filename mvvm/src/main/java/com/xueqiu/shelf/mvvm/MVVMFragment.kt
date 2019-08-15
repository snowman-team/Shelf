package com.xueqiu.shelf.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.xueqiu.shelf.BaseFragment

abstract class MVVMFragment<T : ViewDataBinding, VM : BaseViewModel> : BaseFragment() {

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): VM
    abstract fun getBindingVariable(): Int

    protected val mViewModel by lazy { getViewModel() }
    protected var mViewDataBinding: T? = null

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mViewDataBinding?.root
    }

    @CallSuper
    override fun init() {
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