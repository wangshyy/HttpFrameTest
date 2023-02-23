package com.example.httpframetest.model.api

import okhttp3.OkHttpClient

object ESMSRetrofitClient: BaseRetrofitClient() {

    val service by lazy { getService(ESMSService::class.java, ESMSService.BASE_URL) }

    override fun handleBuilder(builder: OkHttpClient.Builder) {

        builder.addInterceptor { chain ->

//            val token = UserHelper.instance.getUser().token

            var request = chain.request()

            request = request.newBuilder()
//                    .addHeader("token", token?:"")
                    .build()

            val response = chain.proceed(request)

            response
        }

    }

}
