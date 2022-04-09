package ui.smartpro.sequenia.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import org.koin.android.ext.android.inject
import timber.log.Timber
import ui.smartpro.sequenia.R
import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.databinding.FragmentMainBinding
import ui.smartpro.sequenia.debug.FirebaseAnalytics
import ui.smartpro.sequenia.extensions.CommonConstants.listGenre
import ui.smartpro.sequenia.presentation.base.BaseFragment
import ui.smartpro.sequenia.presentation.common.AppState
import ui.smartpro.sequenia.presentation.contract.Contract
import ui.smartpro.sequenia.presentation.main.MainPresenter
import ui.smartpro.sequenia.presentation.main.adapter.MovieAdapter
import ui.smartpro.sequenia.presentation.main.filmadapter.FilmsAdapter
import ui.smartpro.sequenia.presentation.main.filmadapter.OnFilmClickListener
import ui.smartpro.sequenia.presentation.movie_details.FilmDetailFragment.Companion.BUNDLE_EXTRA

class MainFragment(override val layoutId: Int = R.layout.fragment_main,
) : BaseFragment<FragmentMainBinding, AppState, Contract.View, Contract.Presenter>(),
Contract.View, OnFilmClickListener {

//    override val presenter: Contract.Presenter by inject()
    override val presenter: MainPresenter by inject()
    private var fimsList: List<Film> = listOf()

    private var adapter = MovieAdapter(this)
    private var adapterMovie: FilmsAdapter? = null

    private val analytics by inject<FirebaseAnalytics>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Главная"

        presenter.attach(this)
        presenter.getFilms()

        adapter = MovieAdapter(this)
        binding.mainRecycler.adapter = adapter
        binding.mainRecycler.apply {
            adapter = adapter
        }

        adapterMovie = FilmsAdapter(this)
        onGenrePicked()    }

    override fun showGenres(genres: List<Genre>) {
        adapter.data = genres
        listGenre = genres
    }

    override fun showFilms(film: List<Film>) {
        presenter.getAllGenres(film)
        Timber.d("$film")
        fimsList = film

    }

    override fun showMoviesByGenres(genre: String, film: List<Film>) {
        analytics.choiseGenre("Выбран жанр",genre)
        adapter.filmList = film
    }

    private fun openFilmDetails(film: Film) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        val bundle = Bundle().also {
            it.putParcelable(BUNDLE_EXTRA, film)
        }

        getOrientation(bundle,options)

    }

    private fun onGenrePicked() {
        adapter.onGenreItemClick = { it, pos ->
            presenter.showMoviesByGenres(listGenre[pos - 1].name)
            Timber.tag("onGenrePicked").d("${pos}" + "/" + listGenre[pos - 1])
            listGenre.forEachIndexed { index, genre ->
                when {
                    (index != pos - 1) -> genre.isChecked = false
                    (index == pos - 1 && genre.isChecked) -> genre.isChecked = false
                    (index == pos - 1 && !genre.isChecked) -> genre.isChecked = true
                }
            }
            Timber.tag("onGenrePicked").d(listGenre.toString())
        }
    }

    private fun getOrientation(bundle: Bundle, options: NavOptions) {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).also {
                it.navigate(R.id.action_mainFragment_to_movieDetailFragment, bundle, options)
            }
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).also {
                it.navigate(R.id.action_mainFragment_to_movieDetailFragment, bundle, options)
            }
        }
    }

    override fun onItemFilmClickListener(film: Film) {
        openFilmDetails(film)
        analytics.choiseGenre("Выбран фильм",film.name)
    }

    override fun onPause() {
        super.onPause()
        presenter.detach(null)
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }
}