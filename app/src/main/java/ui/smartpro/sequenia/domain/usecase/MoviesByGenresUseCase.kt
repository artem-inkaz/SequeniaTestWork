package ui.smartpro.sequenia.domain.usecase

import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.domain.repository.MoviesByGenres

class MoviesByGenresUseCase(private val moviesByGenres: MoviesByGenres) {
    fun getFilmsByGenres(genre: String,film: List<Film>): HashMap<String, List<Film>>{
       return moviesByGenres.getMoviesGenres(genre, film)
    }
}