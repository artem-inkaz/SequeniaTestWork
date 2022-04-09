package ui.smartpro.sequenia.presentation.common

import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film

sealed class AppState {
    data class SuccessGenre(val data: List<Genre>?) : AppState()
    data class SuccessFilm(val data: List<Film>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
