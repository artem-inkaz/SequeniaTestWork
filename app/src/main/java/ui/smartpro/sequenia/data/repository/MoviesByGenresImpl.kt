package ui.smartpro.sequenia.data.repository

import ui.smartpro.sequenia.data.mappers.MovieMapper.getFilmsByGenres
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.domain.repository.MoviesByGenres

class MoviesByGenresImpl:MoviesByGenres {
    override fun getMoviesGenres(genre: String, film: List<Film>): HashMap<String, List<Film>> {
        return getFilmsByGenres(genre,film)
    }
}