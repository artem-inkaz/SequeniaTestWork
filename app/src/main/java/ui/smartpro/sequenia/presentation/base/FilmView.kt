package ui.smartpro.sequenia.presentation.base

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import moxy.viewstate.strategy.alias.SingleState
import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film

interface FilmView:MvpView,BaseView {

    @OneExecution
    fun showOnBoarding()
    @OneExecution
    fun showAlertConnect()
    @SingleState
    fun showFilms(films: List<Film>)
    @SingleState
    fun showGenres(genres: List<Genre>)
    @SingleState
    fun showMoviesByGenres(genre: String, film: List<Film>)
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun firstOpen(count:Int)
    @AddToEndSingle
    fun networkStatus(status: Boolean)
}