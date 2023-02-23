package com.example.httpframetest.model.api

import com.example.httpframetest.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseRetrofitClient {

    companion object {
        private const val TIME_OUT = 5  //超时时间
    }

    private val client: OkHttpClient //okhttp客户端
        get() {
            val builder = OkHttpClient.Builder()    //构建okhttpClient实例
            val logging = HttpLoggingInterceptor()  //拦截器实例
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }

            builder.addInterceptor(logging) //添加拦截器
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)    //设置超时时间

            handleBuilder(builder)

            return builder.build()
        }

    protected abstract fun handleBuilder(builder: OkHttpClient.Builder)

    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()   //构建retrofit实例
            .client(client) //设置客户端
            .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器 使用Gson进行数据解析
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //设置网路执行适配器，CallAdapter.Factory作用：网络执行适配器工厂类，会根据接口里面定义的类型来找到合适的适配器。 使用rxjava
//                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .baseUrl(baseUrl)   //设置网络请求baseUel地址
            .build().create(serviceClass)
    }
}