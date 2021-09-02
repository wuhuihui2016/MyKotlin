package com.whh.mykotlin.mvvm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Student() {

    // 主键，自动增长
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "age")
    var age: Int = 0

    //解决 There are multiple good constructors and Room will pick the no-arg constructor. You can use the @Ignore annotation to eliminate unwanted constructors.问题
    @Ignore
    constructor(name: String, age: Int) : this() {
        this.name = name
        this.age = age
    }

}