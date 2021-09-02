package com.whh.mykotlin.exdemo.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.whh.mykotlin.R
import com.whh.mykotlin.exdemo.base.BaseActivity
import com.whh.mykotlin.exdemo.common.Flag
import com.whh.mykotlin.exdemo.entity.LoginRegisterResponse
import com.whh.mykotlin.exdemo.presenter.LoginPresenter
import com.whh.mykotlin.exdemo.presenter.LoginPresenterImpl
import com.whh.mykotlin.exdemo.view.LoginView
import kotlinx.android.synthetic.main.activity_user_login.*

class LoginActivity : BaseActivity<LoginPresenter>(), LoginView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        hideActionBar()
        user_login_bt.setOnClickListener(onCLickLister)

        user_register_tv.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private val onCLickLister = View.OnClickListener { view ->
        when (view.id) {
            R.id.user_login_bt -> {
                val userNameStr = user_phone_et.text.toString()
                val userPwdStr = user_password_et.text.toString()
                Log.d(Flag.TAG, "userName:$userNameStr,  userPwd:$userPwdStr")
                presenter.loginAction(this@LoginActivity, userNameStr, userPwdStr)
            }
        }
    }

    override fun loginSuccess(loginBean: LoginRegisterResponse?) {
        Toast.makeText(this@LoginActivity, "登录成功😀", Toast.LENGTH_SHORT).show()
//        val intent = Intent(this@LoginActivity,  MainActivity::class.java)
//        startActivity(intent)
    }

    override fun loginFialure(erroeMsg: String?) {
        Toast.makeText(this@LoginActivity, "登录失败！$erroeMsg", Toast.LENGTH_SHORT).show()
    }

    override fun createP(): LoginPresenter = LoginPresenterImpl(this)

    override fun recycle() {
        presenter.unAttachView()
    }
}
