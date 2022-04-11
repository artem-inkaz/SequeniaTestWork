package ui.smartpro.sequenia.presentation.main

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
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

class MainPresenter : BasePresenter<Contract.View>(),Contract.Presenter,Contract.State, KoinComponent {

    private val moviesUseCase: MoviesUseCase by inject()
    private val genresUseCase: GenresUseCase by inject()
    private val moviesByGenresUseCase: MoviesByGenresUseCase by inject()
    private var state: Contract.State? = null
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
                    Log.w("Lifecycle","onResponse /$films")
                }
                if (response.isSuccessful) viewState.hideLoader()
            }

        override fun onFailure(
            call: Call<ui.smartpro.sequenia.data.response.Response>,
            t: Throwable
        ) {
            Timber.d(t.toString())
            viewState.showError(t.toString())
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        moviesUseCase.getMovies(callBack)
    }

    fun showMoviesByGenres(genre: String) {
        val genresUseCase = moviesByGenresUseCase.getFilmsByGenres(genre, films)
        genresUseCase[genre]?.let { viewState?.showMoviesByGenres(genre, it) }
        Log.w("Lifecycle","showMoviesByGenres /$films")
    }

    override fun getFilms() {
        moviesUseCase.getMovies(callBack)
    }

    override fun getAllGenres(genres: List<Film>): List<Genre> {
       return genresUseCase.getAllGenres(genres)
    }

    override fun getLastFilmsItems(): List<Film>? {
        TODO("Not yet implemented")
    }

    override fun getLastGenresPos(): Int? {
        TODO("Not yet implemented")
    }

    override fun getLastGenresName(): String? {
        TODO("Not yet implemented")
    }
}