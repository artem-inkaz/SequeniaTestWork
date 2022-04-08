package ui.smartpro.sequenia.presentation.main.filmadapter

import ui.smartpro.sequenia.data.response.Film

interface OnFilmClickListener {
    fun onItemFilmClickListener(film: Film)
}