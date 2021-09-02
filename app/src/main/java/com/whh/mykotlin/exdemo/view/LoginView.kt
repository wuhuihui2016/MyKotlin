package com.whh.mykotlin.exdemo.view

import com.whh.mykotlin.exdemo.entity.LoginRegisterResponse

interface LoginView {

    fun loginSuccess(loginBean: LoginRegisterResponse?)

    fun loginFialure(erroeMsg: String?)
}