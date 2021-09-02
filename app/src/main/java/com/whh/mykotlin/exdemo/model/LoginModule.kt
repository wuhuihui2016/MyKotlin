package com.whh.mykotlin.exdemo.model

import android.content.Context
import com.whh.mykotlin.exdemo.presenter.LoginPresenter

interface LoginModule {

    // 取消请求 动作
    fun cancelRequest()

    // 登录
    fun login(
        context: Context,
        username: String,
        password: String,
        onLoginListener: LoginPresenter.OnLoginListener
        )

}