package com.whh.mykotlin.exdemo.presenter

import android.content.Context
import com.whh.mykotlin.exdemo.base.IBasePresenter
import com.whh.mykotlin.exdemo.entity.LoginRegisterResponse

interface LoginPresenter : IBasePresenter {

    fun loginAction(context: Context, username: String, password: String)

    interface OnLoginListener {
        fun loginSuccess(loginBean: LoginRegisterResponse?)

        fun loginFialure(erroeMsg: String?)
    }
}