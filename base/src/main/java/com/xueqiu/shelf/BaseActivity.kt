package com.xueqiu.shelf

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {

    protected var mRetention: Long = 0
    protected var mResumeTime: Long = -1
    protected var mPauseTime: Long = -1

    private var mCompositeDisposable = CompositeDisposable()

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable = CompositeDisposable()
        }
        beforeSetView()
        setPageView()
        beforeInit()
        init()
    }

    abstract fun setPageView()

    abstract fun init()

    protected open fun beforeSetView() {}

    protected open fun beforeInit() {}

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        dispose()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        mResumeTime = System.currentTimeMillis()
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        mPauseTime = System.currentTimeMillis()
        mRetention += mPauseTime - mResumeTime
    }

    protected fun resurgence() {
        val intent = Intent(this, this::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.shelf_fade_in, R.anim.shelf_fade_out)
        finish()
    }

    protected fun addDispose(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    protected fun dispose() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    protected fun isInactive() = isDestroyed || isFinishing

    fun Disposable?.autoDispose() {
        this?.let { addDispose(it) }
    }

    protected fun floatStatusBar() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }

    protected fun floatNavigationBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
    }

    protected fun setStatusBarColor(@ColorRes color: Int) {
        val statusBarColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            resources.getColor(color, theme)
        } else {
            @Suppress("DEPRECATION")
            resources.getColor(color)
        }
        if (statusBarColor == Color.BLACK && window.navigationBarColor == Color.BLACK) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        window.statusBarColor = statusBarColor
    }

    protected fun setNavigationBar(@ColorRes color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.navigationBarColor = resources.getColor(color, theme)
        } else {
            @Suppress("DEPRECATION")
            window.navigationBarColor = resources.getColor(color)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected fun setStatusBarLightStyle(isLight: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isLight) {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or window.decorView.systemUiVisibility
            } else {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() and window.decorView.systemUiVisibility
            }
        }
    }

}