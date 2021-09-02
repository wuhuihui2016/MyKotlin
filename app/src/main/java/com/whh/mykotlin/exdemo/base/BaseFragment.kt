package com.whh.mykotlin.exdemo.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseFragment<P> : Fragment() where P : IBasePresenter {
    lateinit var p: P
    private lateinit var mActivity: AppCompatActivity // Fragment 所在的 Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p = getPresenter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    abstract fun getPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
        recycle()
    }

    abstract fun createOK()
    abstract fun recycle()

    fun hideActionBar() {
        val actionBar: ActionBar? = mActivity?.supportActionBar
        actionBar?.hide();
    }

    fun showActionBar() {
        mActivity?.supportActionBar?.show()
    }

}