package com.whh.mykotlin.exdemo.model

import android.content.Context
import com.whh.mykotlin.exdemo.presenter.LoginPresenter
import com.whh.mykotlin.exdemo.presenter.RegisterPersenter

interface RegisterModel {
    fun cancelRequest()

    fun register(
        context: Context,
        username: String,
        password: String,

        onRegisterListener: RegisterPersenter.OnRegisterListener
    )

}