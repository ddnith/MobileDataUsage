package com.example.mobiledatausage.di

import android.content.Context
import com.example.mobiledatausage.model.ApiInterface
import com.example.mobiledatausage.model.MobileDataUsage
import com.example.mobiledatausage.model.RetrofitHelper
import com.example.mobiledatausage.model.repository.MainRepository
import com.example.mobiledatausage.model.repository.MainRepositoryImpl
import com.example.mobiledatausage.tracking.Tracker
import com.example.mobiledatausage.ui.detail.DetailViewModel
import com.example.mobiledatausage.ui.list.ListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.math.sin

val appModule = module {
    single {
        RetrofitHelper(androidContext()).getInstance().create(ApiInterface::class.java)
    }

    single<MainRepository> { MainRepositoryImpl(get()) }

    viewModel() { ListViewModel(get()) }

    viewModel() { DetailViewModel(get()) }

    single {
        Tracker
    }

}