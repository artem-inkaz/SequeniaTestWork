package ui.smartpro.sequenia.domain.usecase

import retrofit2.Callback
import ui.smartpro.sequenia.data.response.Response
import ui.smartpro.sequenia.domain.repository.MoviesRepository

class MoviesUseCase(private val moviesRepository: MoviesRepository) {
    fun getMovies(callback: Callback<Response>) {
    return moviesRepository.getMovies(callback)
    }
}