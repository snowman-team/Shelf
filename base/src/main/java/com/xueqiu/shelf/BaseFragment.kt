package com.xueqiu.shelf

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected var mRetention: Long = 0
    protected var mResumeTime: Long = -1
    protected var mPauseTime: Long = -1

    protected var hasInit = false

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beforeInit()
        if (!hasInit) {
            init()
            hasInit = true
        }
        onPageShow()
    }

    open fun beforeInit() {}

    @CallSuper
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && !hasInit) {
            init()
            hasInit = true
        }
        if (isVisibleToUser) {
            onVisible()
        } else {
            onInvisible()
        }
    }

    @CallSuper
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onVisible()
    }

    @CallSuper
    override fun onDetach() {
        super.onDetach()
        onInvisible()
    }

    @CallSuper
    protected open fun onVisible() {
        mResumeTime = System.currentTimeMillis()
    }

    @CallSuper
    protected open fun onInvisible() {
        mPauseTime = System.currentTimeMillis()
        mRetention += mPauseTime - mResumeTime
    }

    protected open fun init() {}

    protected open fun onPageShow() {}

}
