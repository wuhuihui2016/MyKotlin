package com.whh.mykotlin.exdemo.view

import com.whh.mykotlin.exdemo.entity.LoginRegisterResponse

interface RegisterView {

    fun registerSuccess(registerBean: LoginRegisterResponse?)

    fun registerFialure(erroeMsg: String?)
}