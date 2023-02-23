package com.example.httpframetest.core

import com.example.httpframetest.MainActivityViewModel
import com.example.httpframetest.model.api.ESMSRetrofitClient
import com.example.httpframetest.model.api.ESMSService
import com.example.httpframetest.model.repository.DataRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *  author : wsy
 *  date   : 2023/2/23
 *  desc   :
 */

val viewModelModule = module {
    viewModel { MainActivityViewModel(get()) }
}

val repositoryModule = module {
    //single，单例对象，多次依赖只产生一个对象
    //factory,工厂对象，每次依赖产生一个新对象
    single { ESMSRetrofitClient.getService(ESMSService::class.java, ESMSService.BASE_URL) }
    single { DataRepository(get()) }
}

val appModule = listOf(viewModelModule, repositoryModule)