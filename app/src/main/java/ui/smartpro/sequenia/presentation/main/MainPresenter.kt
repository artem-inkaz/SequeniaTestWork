package ui.smartpro.sequenia.presentation.main

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.domain.usecase.GenresUseCase
import ui.smartpro.sequenia.domain.usecase.MoviesByGenresUseCase
import ui.smartpro.sequenia.domain.usecase.MoviesUseCase
import ui.smartpro.sequenia.presentation.base.BasePresenter
import ui.smartpro.sequenia.presentation.common.AppState
import ui.smartpro.sequenia.presentation.contract.Contract

class MainPresenter : BasePresenter<AppState, Contract.View>(), Contract.Presenter, KoinComponent {

    private val moviesUseCase: MoviesUseCase by inject()
    private val genresUseCase: GenresUseCase by inject()
    private val moviesByGenresUseCase: MoviesByGenresUseCase by inject()
    private var currentView: Contract.View? = null
    private var films: List<Film> = listOf()

        private val callBack = object :
        Callback<ui.smartpro.sequenia.data.response.Response> {

        override fun onResponse(
            call: Call<ui.smartpro.sequenia.data.response.Response>,
            response: Response<ui.smartpro.sequenia.data.response.Response>
        ) {
            val responseServer = response.body()!!
            val movies = response.body()!!.films
            if (responseServer != null) {
                films = movies
                currentView?.showFilms(movies)
            }
        }

        override fun onFailure(
            call: Call<ui.smartpro.sequenia.data.response.Response>,
            t: Throwable
        ) {
            Timber.d(t.toString())
        }
    }

    fun showMoviesByGenres(genre: String) {
        val genresUseCase = moviesByGenresUseCase.getFilmsByGenres(genre, films)
        genresUseCase[genre]?.let { currentView?.showMoviesByGenres(genre, it) }
    }

    override fun getFilms() {
        return moviesUseCase.getMovies(callBack)
    }

    override fun getAllGenres(genres: List<Film>): List<Genre> {
        AppState.SuccessGenre(genresUseCase.getAllGenres(genres))
        currentView?.showGenres(genresUseCase.getAllGenres(genres))
        return genresUseCase.getAllGenres(genres)
    }

    override fun attach(view: Contract.View) {
        super.attach(view)
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detach(view: Contract.View?) {
        if (view != null) {
            super.detach(view)
        }
        if (view == currentView) {
            currentView = null
        }
    }
}