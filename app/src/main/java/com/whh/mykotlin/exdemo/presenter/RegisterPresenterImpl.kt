package com.whh.mykotlin.exdemo.presenter

import android.content.Context
import com.whh.mykotlin.exdemo.entity.LoginRegisterResponse
import com.whh.mykotlin.exdemo.model.RegisterModelImpl
import com.whh.mykotlin.exdemo.view.RegisterView

class RegisterPresenterImpl(var registerView: RegisterView?) : RegisterPersenter,
    RegisterPersenter.OnRegisterListener {

    private val registerModelImpl: RegisterModelImpl = RegisterModelImpl()

    override fun register(context: Context, username: String, password: String) {
        registerModelImpl.register(context, username, password, this)
    }

    override fun unAttachView() {
        registerView = null
        registerModelImpl.cancelRequest()
    }

    override fun registerSuccess(loginBean: LoginRegisterResponse?) {
        registerView?.registerSuccess(loginBean)
    }

    override fun registerFailed(errorMsg: String?) {
        registerView?.registerFialure(errorMsg)
    }


}


