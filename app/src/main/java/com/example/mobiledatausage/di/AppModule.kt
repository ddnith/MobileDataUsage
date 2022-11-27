package com.example.mobiledatausage.di

import com.example.mobiledatausage.model.ApiInterface
import com.example.mobiledatausage.model.RetrofitHelper
import com.example.mobiledatausage.model.repository.MainRepository
import com.example.mobiledatausage.model.repository.MainRepositoryImpl
import com.example.mobiledatausage.tracking.Tracker
import com.example.mobiledatausage.ui.detail.DetailViewModel
import com.example.mobiledatausage.ui.list.ListViewModel
import com.example.mobiledatausage.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        RetrofitHelper(androidContext()).getInstance().create(ApiInterface::class.java)
    }

    single<MainRepository> { MainRepositoryImpl(get()) }

    viewModel() { ListViewModel(get(), get()) }

    viewModel() { DetailViewModel(get(), get()) }

    single {
        Tracker
    }

    single<DispatcherProvider> {
        object : DispatcherProvider {
            override val main: CoroutineDispatcher
                get() = Dispatchers.Main
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
            override val default: CoroutineDispatcher
                get() = Dispatchers.Default
            override val unconfined: CoroutineDispatcher
                get() = Dispatchers.Unconfined

        }
    }

}