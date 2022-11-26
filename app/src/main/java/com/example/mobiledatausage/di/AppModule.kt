package com.example.mobiledatausage.di

import com.example.mobiledatausage.model.ApiInterface
import com.example.mobiledatausage.model.RetrofitHelper
import com.example.mobiledatausage.model.repository.MainRepository
import com.example.mobiledatausage.model.repository.MainRepositoryImpl
import com.example.mobiledatausage.viewmodels.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        RetrofitHelper.getInstance().create(ApiInterface::class.java)
    }

    single<MainRepository> { MainRepositoryImpl(get()) }

    viewModel<ListViewModel>() { ListViewModel(get()) }
}