package com.uma.cipltask

import android.app.Application
import com.uma.cipltask.di.AppModule
import com.uma.cipltask.di.DaggerAppComponant
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApplication : Application(), HasAndroidInjector {

    @Inject lateinit var mInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponant.builder().appModule(AppModule(this)).build().inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> {
        return mInjector
    }
}