package com.xueqiu.shelf.app.mvvm

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xueqiu.shelf.app.R
import com.xueqiu.shelf.app.databinding.ActivitTestMvvmBinding
import com.xueqiu.shelf.mvvm.BR
import com.xueqiu.shelf.mvvm.MVVMActivity
import kotlinx.android.synthetic.main.activit_test_mvvm.*

class TestMVVMActivity : MVVMActivity<ActivitTestMvvmBinding, TestViewModel>() {

    override fun getViewModel(): TestViewModel = ViewModelProviders.of(this).get(TestViewModel::class.java)

    override fun getLayoutId(): Int = R.layout.activit_test_mvvm

    override fun getBindingVariable(): Int = BR.testViewModel

    override fun init() {
        super.init()

        btn_add.setOnClickListener {
            mViewModel.addOne()
        }
        mViewModel.initData()
    }

    override fun observerDataChanged() {
        mViewModel.testModel.observe(this, Observer {
            Toast.makeText(this, "new count -> ${it.count}", Toast.LENGTH_SHORT).show()
        })
    }

}