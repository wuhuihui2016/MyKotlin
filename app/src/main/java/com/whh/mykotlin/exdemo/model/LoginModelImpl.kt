package com.whh.mykotlin.exdemo.model

import android.content.Context
import com.whh.mykotlin.exdemo.common.APIClient
import com.whh.mykotlin.exdemo.common.APIResponse
import com.whh.mykotlin.exdemo.common.WanAndroidAPI
import com.whh.mykotlin.exdemo.entity.LoginRegisterResponse
import com.whh.mykotlin.exdemo.presenter.LoginPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginModelImpl : LoginModule {
    override fun cancelRequest() {
    }

    override fun login(
        context: Context,
        username: String,
        password: String,
        onLoginListener: LoginPresenter.OnLoginListener) {
        APIClient.instance.instanceRetrofit(WanAndroidAPI::class.java)
            .loginAction(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : APIResponse<LoginRegisterResponse>(context) {
                override fun success(data: LoginRegisterResponse?) {
                    onLoginListener.loginSuccess(data)
                }

                override fun failure(errorMsg: String?) {
                    onLoginListener.loginFialure(errorMsg)
                }

            })
    }
}
