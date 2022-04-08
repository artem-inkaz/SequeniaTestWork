package ui.smartpro.sequenia.domain.repository

import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film

interface GenresRepository {
    fun getAllGenres(genres: List<Film>): List<Genre>
}