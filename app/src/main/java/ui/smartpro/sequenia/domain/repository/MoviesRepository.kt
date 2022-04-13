package ui.smartpro.sequenia.domain.repository

import retrofit2.Callback
import ui.smartpro.sequenia.data.response.Response

interface MoviesRepository {
    fun getMovies(callback: Callback<Response>)
}