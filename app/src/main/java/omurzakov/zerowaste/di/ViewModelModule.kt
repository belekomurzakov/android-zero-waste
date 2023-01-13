package omurzakov.zerowaste.di

import omurzakov.zerowaste.ui.screens.camera.CameraViewModel
import omurzakov.zerowaste.ui.screens.category.CategoryViewModel
import omurzakov.zerowaste.ui.screens.detail.DetailViewModel
import omurzakov.zerowaste.ui.screens.historydetail.HistoryDetailViewModel
import omurzakov.zerowaste.ui.screens.home.HomeViewModel
import omurzakov.zerowaste.ui.screens.map.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CategoryViewModel() }
    viewModel { MapViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { CameraViewModel(get()) }
    viewModel { HistoryDetailViewModel(get()) }
}