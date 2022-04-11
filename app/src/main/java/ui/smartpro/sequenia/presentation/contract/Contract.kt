package ui.smartpro.sequenia.presentation.contract

import moxy.MvpView
import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.presentation.base.FilmView

interface Contract {

    interface View : FilmView

    interface Presenter : MvpView {

        fun getFilms()
        fun getAllGenres(genres: List<Film>): List<Genre>
    }
}
