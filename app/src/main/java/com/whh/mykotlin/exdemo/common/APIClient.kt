package com.whh.mykotlin.exdemo.common

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {

    private object Holder {
        val INSTANCE = APIClient()
    }

    // 派生
    companion object {

        val instance = Holder.INSTANCE

    }




    // WanAndroidAPI实例化这个，  XXXAPI实例化这个，   BBBAPI实例化
    fun <T> instanceRetrofit(apiInterface: Class<T>): T {

        val loggingInterceptor = HttpLoggingInterceptor(){
            Log.i("logInterceptor", it)
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        // OKHttpClient请求服务器
        val mOkHttpClient = OkHttpClient().newBuilder().myApply {

            // 添加读取超时时间
            readTimeout(10000, TimeUnit.SECONDS)

            // 添加连接超时时间
            connectTimeout(10000, TimeUnit.SECONDS)

            // 添加写出超时时间
            writeTimeout(10000, TimeUnit.SECONDS)

            addInterceptor(loggingInterceptor)

        }.build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com")

            // 请求方  ←
            .client(mOkHttpClient)

            // 响应方  →
            // Response的事情  回来
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // RxJava来处理
            .addConverterFactory(GsonConverterFactory.create()) // Gson 来解析 --- JavaBean
            .build()

        return retrofit.create(apiInterface);
    }
}

// 默认 无参数的
fun <T> T.myApply(mm: T.() -> Unit): T {
    // T == this
    mm()
    return this
}