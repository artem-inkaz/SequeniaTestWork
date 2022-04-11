package ui.smartpro.sequenia.presentation.main

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.model.CountersModel
import ui.smartpro.sequenia.data.model.NetworkStateProvider
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.domain.usecase.GenresUseCase
import ui.smartpro.sequenia.domain.usecase.MoviesByGenresUseCase
import ui.smartpro.sequenia.domain.usecase.MoviesUseCase
import ui.smartpro.sequenia.presentation.base.BasePresenter
import ui.smartpro.sequenia.presentation.contract.Contract

class MainPresenter : BasePresenter<Contract.View>(), Contract.Presenter,
    KoinComponent {

    private val model: CountersModel by inject()
    private val moviesUseCase: MoviesUseCase by inject()
    private val genresUseCase: GenresUseCase by inject()
    private val moviesByGenresUseCase: MoviesByGenresUseCase by inject()
    private val networkStateProvider: NetworkStateProvider by inject()
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
                viewState.showFilms(movies)
                viewState.showGenres(genresUseCase.getAllGenres(movies))
            }
            if (response.isSuccessful) viewState.hideLoader()
        }

        override fun onFailure(
            call: Call<ui.smartpro.sequenia.data.response.Response>,
            t: Throwable
        ) {
            Timber.d(t.toString())
            viewState.showError(t.toString())
            viewState.hideLoader()
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.networkStatus(networkStateProvider.isNetworkAvailable())
        if (networkStateProvider.isNetworkAvailable()) {
            viewState.firstOpen(0)
            moviesUseCase.getMovies(callBack)
            viewState.showOnBoarding()
        } else {
            viewState.showAlertConnect()
        }
    }

    fun checkConnection() {
        if (!networkStateProvider.isNetworkAvailable()) {
            viewState.showAlertConnect()
        }
    }

    fun firstOpen() {
        model.incrementCounter(0)
            .let(viewState::firstOpen)
    }

    fun showMoviesByGenres(genre: String) {
        if (networkStateProvider.isNetworkAvailable()) {
            val genresUseCase = moviesByGenresUseCase.getFilmsByGenres(genre, films)
            genresUseCase[genre]?.let { viewState?.showMoviesByGenres(genre, it) }
        }
    }

    override fun getFilms() {
        if (networkStateProvider.isNetworkAvailable()) {
            moviesUseCase.getMovies(callBack)
        }
    }

    override fun getAllGenres(genres: List<Film>): List<Genre> {
        if (networkStateProvider.isNetworkAvailable()) {
            return genresUseCase.getAllGenres(genres)
        } else {
            return listOf()
        }
    }
}