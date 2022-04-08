package ui.smartpro.sequenia.domain.repository

import ui.smartpro.sequenia.data.response.Film

interface MoviesByGenres {
    fun getMoviesGenres(genre: String,film: List<Film>):HashMap<String,List<Film>>
}