package com.whh.mykotlin.exdemo.presenter

import android.content.Context
import com.whh.mykotlin.exdemo.base.IBasePresenter
import com.whh.mykotlin.exdemo.entity.LoginRegisterResponse

interface RegisterPersenter : IBasePresenter{

    fun register(context:Context, username:String, password:String)

    interface OnRegisterListener {

        fun registerSuccess(registerBean: LoginRegisterResponse?)

        fun registerFailed(errorMsg: String?)

    }
}