package com.whh.mykotlin.mvvm

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class StudentViewModel(application: Application) : AndroidViewModel(application) {

    var studentDao: StudentDao? = null
    var students: LiveData<MutableList<Student>>? = null //动态更新数据

    init { //类似于 java 的 static 方法块
        val studentDatabase: StudentDatabase? = StudentDatabase.getDatabase()
        if (studentDatabase != null) {
            studentDao = studentDatabase.getStudentDao()
            Log.e("whh0830", "studentDao=" + studentDao)
            students = studentDao!!.queryAllStudents()
        }
    }

    // TODO 增加   vararg:可变参数
    fun insertStudents(vararg students: Student) {
        studentDao?.insertStudents(*students)
    }

    // TODO 更新
    fun updateStudents(vararg students: Student) {
        studentDao?.updateStudents(*students)
    }

    // TODO 删除
    fun deleteStudents(vararg students: Student) {
        studentDao?.deleteStudents(*students)
    }

    // TODO 全部查询
    fun queryAllStudent() {
        students = studentDao?.queryAllStudents()!!
    }

    // TODO 全部查询
    fun queryStudentsByDesc() {
        students = studentDao?.queryStudentsByDesc()!!
    }

    // TODO 根据年龄查询
    fun queryStudents4Age(age: Int) {
        students = studentDao?.queryStudents4Age(age)!!
    }

    @JvmName("getStudents1")
    fun getStudents(): LiveData<MutableList<Student>>? {
        return students
    }

}