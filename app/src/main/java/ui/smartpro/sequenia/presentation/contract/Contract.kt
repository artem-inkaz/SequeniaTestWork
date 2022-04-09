package ui.smartpro.sequenia.presentation.contract

import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.presentation.base.MvpPresenter
import ui.smartpro.sequenia.presentation.base.MvpView
import ui.smartpro.sequenia.presentation.common.AppState

interface Contract {

    interface View : MvpView

    interface Presenter : MvpPresenter<AppState, View> {

        fun getFilms()
        fun getAllGenres(genres: List<Film>): List<Genre>
    }
}