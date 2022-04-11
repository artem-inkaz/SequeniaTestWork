package ui.smartpro.sequenia.presentation.contract

import moxy.MvpPresenter
import moxy.MvpView
import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.presentation.base.BasePresenter
import ui.smartpro.sequenia.presentation.base.BaseState
import ui.smartpro.sequenia.presentation.base.FilmView

interface Contract {

    interface View : FilmView

    interface State: BaseState {
        fun getLastFilmsItems(): List<Film>?
        fun getLastGenresPos(): Int?
        fun getLastGenresName(): String?
    }

    interface Presenter : MvpView {

        fun getFilms()
        fun getAllGenres(genres: List<Film>): List<Genre>
    }
}
