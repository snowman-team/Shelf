package com.xueqiu.shelf.mvp

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment

interface IViewer {

    fun getViewerContext(): Context? {
        return when (this) {
            is Activity -> {
                this
            }
            is Fragment -> {
                this.context
            }
            is View -> {
                this.context
            }
            else -> null
        }
    }
}