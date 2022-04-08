package ui.smartpro.sequenia.presentation.main

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ui.smartpro.sequenia.presentation.base.BasePresenter
import timber.log.Timber
import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.mappers.MovieMapper
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.domain.usecase.GenresUseCase
import ui.smartpro.sequenia.domain.usecase.MoviesByGenresUseCase
import ui.smartpro.sequenia.domain.usecase.MoviesUseCase

class MainPresenter(
//    private val moviesRepository: MoviesRepositoryImpl,
//      private val moviesUseCase: MoviesUseCase
//    private val genresRepository: GenresRepositoryImpl
) : BasePresenter<MainPresenter.MovieView>(), KoinComponent {

    private val moviesUseCase: MoviesUseCase by inject()
    private val genresUseCase: GenresUseCase by inject()
    private val moviesbyGenresUseCase: MoviesByGenresUseCase by inject()

    private var films: List<Film> = listOf()
    private val callBack = object :
        Callback<ui.smartpro.sequenia.data.response.Response> {


        override fun onResponse(
            call: Call<ui.smartpro.sequenia.data.response.Response>,
            response: Response<ui.smartpro.sequenia.data.response.Response>
        ) {
            val responseServer = response.body()!!
            val movies = response.body()!!.films
            val genres = response.body()!!.films[1].genres
            if (responseServer != null) {
                val m = MovieMapper.convertToMovies(responseServer)
                val g = MovieMapper.getMapAllGenres(movies)
                val f = MovieMapper.getFilmsByGenres("драма", movies)
                films = movies
                view?.showMovies(movies)
                view?.showGenres(g)
//
            }
        }

        override fun onFailure(
            call: Call<ui.smartpro.sequenia.data.response.Response>,
            t: Throwable
        ) {
            Timber.d(t.toString())
            view?.showEmptyMovies()
        }
    }

    fun getAllGEnres() {
        genresUseCase.getAllGenres(films)

    }

    fun getMovies() {
//        moviesRepository.getMovies(callBack)
        moviesUseCase.getMovies(callBack)
    }

    fun showMoviesByGenres(genre: String, film: List<Film>) {
        val genresUseCase = moviesbyGenresUseCase.getFilmsByGenres(genre, film)
        genresUseCase[genre]?.let { view?.showMoviesByGenres(genre, it) }
    }


    interface MovieView {
        fun showGenres(genres: List<Genre>)
        fun showMovies(film: List<Film>)
        fun showMoviesByGenres(genre: String, film: List<Film>)
        fun showLoading()
        fun hideLoading()
        fun showEmptyMovies()
        fun showError()
    }
}