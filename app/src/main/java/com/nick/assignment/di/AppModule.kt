package com.nick.assignment.di

import com.nick.assignment.data_source.repository.ProductRepository
import com.nick.assignment.ui.viewmodel.ProductViewModel
import com.nick.assignment.utils.RetrofitBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // RetrofitBuilder
    single { RetrofitBuilder }

    // ProductApiService
    single { get<RetrofitBuilder>().createService() }

    // Repository
    single { ProductRepository(get()) }

    // ViewModel
    viewModel { ProductViewModel(get()) }
}