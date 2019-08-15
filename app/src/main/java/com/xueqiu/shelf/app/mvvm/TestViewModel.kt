package com.xueqiu.shelf.app.mvvm

import androidx.lifecycle.MutableLiveData
import com.xueqiu.shelf.mvvm.BaseViewModel

class TestViewModel : BaseViewModel() {

    val testModel by lazy { MutableLiveData<TestModel>() }

    fun initData() {
        testModel.value = TestModel()
    }

    fun addOne() {
        testModel.value?.let {
            it.count++
            testModel.value = it
        }
    }

}