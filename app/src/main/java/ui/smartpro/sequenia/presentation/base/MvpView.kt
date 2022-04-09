package ui.smartpro.sequenia.presentation.base

import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film

interface MvpView {
    fun showGenres(genres: List<Genre>)
    fun showFilms(films: List<Film>)
    fun showMoviesByGenres(genre: String, film: List<Film>)
}