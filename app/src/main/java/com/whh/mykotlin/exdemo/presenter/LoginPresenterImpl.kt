package com.whh.mykotlin.exdemo.presenter

import android.content.Context
import com.whh.mykotlin.exdemo.entity.LoginRegisterResponse
import com.whh.mykotlin.exdemo.model.LoginModelImpl
import com.whh.mykotlin.exdemo.view.LoginView

class LoginPresenterImpl(var loginView: LoginView?) : LoginPresenter,
    LoginPresenter.OnLoginListener {

    private val loginModel: LoginModelImpl = LoginModelImpl()

    override fun loginAction(context: Context, username: String, password: String) {
        loginModel.login(context, username, password, this)
    }

    override fun unAttachView() {
        loginView = null
        loginModel.cancelRequest()
    }

    override fun loginSuccess(loginBean: LoginRegisterResponse?) {
        loginView?.loginSuccess(loginBean)
    }

    override fun loginFialure(erroeMsg: String?) {
        loginView?.loginFialure(erroeMsg)
    }

}


