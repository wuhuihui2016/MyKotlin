package com.whh.mykotlin.exdemo.activity

import android.os.Bundle
import android.widget.Toast
import com.whh.mykotlin.R
import com.whh.mykotlin.exdemo.base.BaseActivity
import com.whh.mykotlin.exdemo.entity.LoginRegisterResponse
import com.whh.mykotlin.exdemo.presenter.RegisterPersenter
import com.whh.mykotlin.exdemo.presenter.RegisterPresenterImpl
import com.whh.mykotlin.exdemo.view.RegisterView
import kotlinx.android.synthetic.main.activity_user_register.*

class RegisterActivity : BaseActivity<RegisterPersenter>(), RegisterView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideActionBar()
        setContentView(R.layout.activity_user_register)
        user_register_bt.setOnClickListener {
            val usernameStr = user_phone_et.text.toString()
            val passwordStr = user_password_et.text.toString()
            presenter.register(this@RegisterActivity, usernameStr, passwordStr)
        }
    }

    override fun createP(): RegisterPersenter = RegisterPresenterImpl(this)

    override fun recycle() {
        presenter.unAttachView()
    }

    override fun registerSuccess(registerBean: LoginRegisterResponse?) {
        Toast.makeText(this, "注册成功😀", Toast.LENGTH_SHORT).show()
    }

    override fun registerFialure(erroeMsg: String?) {
        Toast.makeText(this, "注册失败 ~ 呜呜呜", Toast.LENGTH_SHORT).show()
    }
}