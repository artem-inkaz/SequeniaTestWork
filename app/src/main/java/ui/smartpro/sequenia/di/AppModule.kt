package ui.smartpro.sequenia.di

import org.koin.dsl.module
import ui.smartpro.sequenia.api.Api
import ui.smartpro.sequenia.api.RetrofitModule

val appModule = module {

    single<Api> { RetrofitModule.apiClient}

}