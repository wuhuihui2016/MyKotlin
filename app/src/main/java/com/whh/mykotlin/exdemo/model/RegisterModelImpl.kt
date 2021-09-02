package com.whh.mykotlin.exdemo.model

import android.content.Context
import com.whh.mykotlin.exdemo.common.APIClient
import com.whh.mykotlin.exdemo.common.APIResponse
import com.whh.mykotlin.exdemo.common.WanAndroidAPI
import com.whh.mykotlin.exdemo.entity.LoginRegisterResponse
import com.whh.mykotlin.exdemo.presenter.RegisterPersenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterModelImpl : RegisterModel {
    override fun cancelRequest() {
    }

    override fun register(
        context: Context,
        username: String,
        password: String,
        onRegisterListener: RegisterPersenter.OnRegisterListener
    ) {
        APIClient.instance.instanceRetrofit(WanAndroidAPI::class.java)
            .registerAction(username, password, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : APIResponse<LoginRegisterResponse>(context) {
                override fun success(data: LoginRegisterResponse?) {
                    onRegisterListener.registerSuccess(data)
                }

                override fun failure(errorMsg: String?) {
                    onRegisterListener.registerFailed(errorMsg)
                }

            })
    }

}
