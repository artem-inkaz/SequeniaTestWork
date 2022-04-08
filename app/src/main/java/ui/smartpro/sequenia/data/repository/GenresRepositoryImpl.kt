package ui.smartpro.sequenia.data.repository

import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.mappers.MovieMapper.getMapAllGenres
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.domain.repository.GenresRepository

class GenresRepositoryImpl: GenresRepository {
    override fun getAllGenres(genres: List<Film>): List<Genre> {
        return getMapAllGenres(genres)
    }
}