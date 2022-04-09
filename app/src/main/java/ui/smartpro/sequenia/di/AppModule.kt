package ui.smartpro.sequenia.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ui.smartpro.sequenia.data.api.Api
import ui.smartpro.sequenia.data.api.RetrofitModule
import ui.smartpro.sequenia.data.repository.GenresRepositoryImpl
import ui.smartpro.sequenia.data.repository.MoviesByGenresImpl
import ui.smartpro.sequenia.data.repository.MoviesRepositoryImpl
import ui.smartpro.sequenia.debug.FirebaseAnalytics
import ui.smartpro.sequenia.domain.repository.GenresRepository
import ui.smartpro.sequenia.domain.repository.MoviesByGenres
import ui.smartpro.sequenia.domain.repository.MoviesRepository
import ui.smartpro.sequenia.domain.usecase.GenresUseCase
import ui.smartpro.sequenia.domain.usecase.MoviesByGenresUseCase
import ui.smartpro.sequenia.domain.usecase.MoviesUseCase
import ui.smartpro.sequenia.presentation.main.MainPresenter

val dataModule = module {
    single<Api> { RetrofitModule.apiClient}
}

val domainModule = module {
    single { MoviesUseCase(get()) }
    single { GenresUseCase(get()) }
    single { MoviesByGenresUseCase(get()) }
    single<MoviesRepository> { MoviesRepositoryImpl() }
    single<GenresRepository> { GenresRepositoryImpl() }
    single<MoviesByGenres> { MoviesByGenresImpl() }
}

val analytic = module {
//    single { FirebaseAnalytics.getInstance(androidApplication()) }
    single { FirebaseAnalytics(androidApplication()) }
}

val presentationModule = module {
    factory { MainPresenter() }

}