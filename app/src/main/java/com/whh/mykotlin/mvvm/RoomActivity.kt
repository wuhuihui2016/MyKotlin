package com.whh.mykotlin.mvvm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.whh.mykotlin.R
import kotlinx.android.synthetic.main.activity_list_room.*
import java.util.*

/**
 * 访问数据库，Room + LiveData + ViewModel
 * 条件查询仍失败！！！
 * 注意事项：
 *    1、访问数据库时，首先创建数据库表，默认不能在主线程访问，需要 allowMainThreadQueries
 *    2、"?"加在变量名后，系统在任何情况不会报它的空指针异常。
 *       "!!"加在变量名后，如果对象为null，那么系统一定会报异常！
 *       当变量为空时，增加判断 if（xxx == null）{...}
 *    3、lateinit修饰符，菜鸟不建议使用，报错：lateinit property studentDatas has not been initialized
 *       建议使用 var studentDatas: MutableList<Student>? = null
 *    4、LiveData 更新数据的变量都定义在ViewModel中，如在 RoomActivity 使用时需要另外定义变量(如：students)接收，注意判空赋值
 *
 */
class RoomActivity : AppCompatActivity() {

    val random = Random()
    var studentViewModel: StudentViewModel? = null
    var students: MutableList<Student>? = null
    var studentAdapter: StudentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_room)

        StudentDatabase.getDatabase(this) //初始化数据库

        //recyclerView 初始化，必须先绑定 adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        studentAdapter = StudentAdapter()
        recyclerView.adapter = studentAdapter

        initData() //动态更新数据

        add_Button.setOnClickListener {
            var age = random.nextInt(20) + 1
            var student = Student("whh$age", age)
            studentViewModel?.insertStudents(student)
            studentViewModel?.queryAllStudent()
        }

        del_Button.setOnClickListener {
            if (students?.size!! > 0) {
                studentViewModel?.deleteStudents(students!![0])
            }
            studentViewModel?.queryAllStudent()!!
        }

        alter_Button.setOnClickListener {
            if (students?.size!! > 0) {
                var student = Student("whh", 18);
                studentViewModel?.updateStudents(student);
            }
            studentViewModel?.queryAllStudent()!!
        }

        search_Button.setOnClickListener {
            studentViewModel?.queryStudents4Age(5)!!
        }
    }


    /**
     * 更新数据并刷新UI
     */
    fun initData() {
        studentViewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application).create(StudentViewModel::class.java)
        studentViewModel?.getStudents()?.observe(this, androidx.lifecycle.Observer {
            Log.e("whh0830", "initData Observer..." + it.size)
            if (students == null) {
                students = it
            } else {
                students?.clear()
                students?.addAll(it)
            }
            Log.e("whh0830", "initData Observer...students=$students")
            studentAdapter!!.studentDatas = it
            studentAdapter!!.notifyDataSetChanged()
        })
    }
}