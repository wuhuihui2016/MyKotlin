package com.whh.mykotlin.mvvm

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * SQL语句封装和接口方法定义：增删改查
 * author:wuhuihui 2021.08.30
 */
@Dao
interface StudentDao {

    //查询所有
    @Query("SELECT * FROM Student")
    fun queryAllStudents(): LiveData<MutableList<Student>>

    // 查询排序
    @Query("SELECT * FROM Student ORDER BY age DESC")
    fun queryStudentsByDesc() : LiveData<MutableList<Student>>

    //根据年龄查询
    @Query("SELECT * FROM Student WHERE age > :age")
    fun queryStudents4Age(age: Int): LiveData<MutableList<Student>>

    //插入数据
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudents(vararg students: Student)

    //更新数据
    @Update
    fun updateStudents(vararg students: Student)

    //删除
    @Delete
    fun deleteStudents(vararg students: Student)

}