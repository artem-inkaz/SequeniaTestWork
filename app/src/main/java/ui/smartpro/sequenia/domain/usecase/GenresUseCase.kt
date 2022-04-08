package ui.smartpro.sequenia.domain.usecase

import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.domain.repository.GenresRepository

class GenresUseCase(private val genresRepository: GenresRepository) {
    fun getAllGenres(genres: List<Film>): List<Genre> {
      return genresRepository.getAllGenres(genres)
    }
}