Snowball Android Shelf Library
============

Basic architecture of mvp and mvvm with rxJava.

## Installation

```groovy
repositories {
    maven { url "https://xueqiumobile.bintray.com/maven" }
}

// use databinding
dataBinding {
    enabled = true
}

dependencies {
    // add dependency, please replace x.y.z to the latest version
    implementation "com.xueqiu.shelf:base:x.y.z"
    // if you use mvp
    implementation "com.xueqiu.shelf:mvp:x.y.z"
    // if you use mvvm
    implementation "com.xueqiu.shelf:mvvm:x.y.z"
    kapt "androidx.lifecycle:lifecycle-compiler:$lifecycle"
}
```

## Usage

### BaseActivity & BaseFragment

```kotlin
class YourActivity : BaseActivity() {

    override fun beforeSetView() {
        floatStatusBar()
        floatNavigationBar()
        setStatusBarLightStyle(true)
    }
    
    override fun setPageView() {
        // set view if you need
        setContentView(R.layout.activity_main)
    }

    override fun beforeInit() {
        // do something
    }
    
    override fun init() {
        // do something
    }

}
```

### MVP

Create a class as the bridge between view and presenter.
```kotlin
interface TestMVPContract {

    interface ITestViewer : IViewer {
        fun showCount(count: Int)
    }

    abstract class BaseTestPresenter : BasePresenter<ITestViewer>() {
        abstract fun initData()
        abstract fun addOne()
    }

}
```
Then implement the presenter and view.
```kotlin
class TestPresenter : TestMVPContract.BaseTestPresenter() {

    override fun initData() {
        // your presenter method
    }

    override fun addOne() {
        // your presenter method
    }

}
class TestMVPActivity : MVPActivity<TestMVPContract.BaseTestPresenter, TestMVPContract.ITestViewer>(),
    TestMVPContract.ITestViewer {

    override fun createPresenter(): TestMVPContract.BaseTestPresenter = TestPresenter() // your presenter

    override fun getLayoutId(): Int = R.layout.activit_test_mvvm // your layout id

    override fun init() {
        // do something
    }

    override fun showCount(count: Int) {
        // your view method
    }
}
```

### MVVM

Before using it, please read [this doc](https://developer.android.com/topic/libraries/data-binding) first.
```kotlin
class TestViewModel : BaseViewModel() {

    val testModel by lazy { MutableLiveData<TestModel>() } // your model

    // your method
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
class TestMVVMActivity : MVVMActivity<ActivitTestMvvmBinding, TestViewModel>() {

    override fun getViewModel(): TestViewModel = ViewModelProviders.of(this).get(TestViewModel::class.java) // your viewModel

    override fun getLayoutId(): Int = R.layout.activit_test_mvvm // your layout id

    override fun getBindingVariable(): Int = BR.testViewModel // generated by dataBinding

    override fun init() {
        super.init()
        // do something
    }

    override fun observerDataChanged() {
        // add observe callback
    }

}
```

### AutoDispose
If you use rx, you can use auto dispose in activity & fragment & presenter & viewModel
```kotlin
Observable.fromCallable { 
    // do something
}.subscribe { 
    // do something
}.autoDispose()
```