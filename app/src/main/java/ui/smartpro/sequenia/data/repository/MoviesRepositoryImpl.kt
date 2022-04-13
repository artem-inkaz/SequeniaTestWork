package ui.smartpro.sequenia.data.repository

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Callback
import ui.smartpro.sequenia.data.api.Api
import ui.smartpro.sequenia.domain.repository.MoviesRepository

class MoviesRepositoryImpl: MoviesRepository, KoinComponent {

    private val apiClient: Api by inject()

    override fun getMovies(callback: Callback<ui.smartpro.sequenia.data.response.Response>) {
     return apiClient.getFilms().enqueue(callback)
    }
}