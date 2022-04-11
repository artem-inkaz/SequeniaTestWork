package ui.smartpro.sequenia.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import moxy.MvpPresenter
import org.koin.android.ext.android.inject
import timber.log.Timber
import ui.smartpro.sequenia.MainActivity
import ui.smartpro.sequenia.R
import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.databinding.FragmentMainBinding
import ui.smartpro.sequenia.debug.FirebaseAnalytics
import ui.smartpro.sequenia.presentation.base.BaseFragment
import ui.smartpro.sequenia.presentation.contract.Contract
import ui.smartpro.sequenia.presentation.dialog.AlertDialogFragment
import ui.smartpro.sequenia.presentation.main.MainPresenter
import ui.smartpro.sequenia.presentation.main.adapter.MovieAdapter
import ui.smartpro.sequenia.presentation.main.filmadapter.FilmsAdapter
import ui.smartpro.sequenia.presentation.main.filmadapter.OnFilmClickListener
import ui.smartpro.sequenia.presentation.movie_details.FilmDetailFragment.Companion.BUNDLE_EXTRA
import ui.smartpro.sequenia.utils.CommonConstants.listGenre
import ui.smartpro.sequenia.utils.SharedPreferencesHelper
import kotlin.system.exitProcess

class MainFragment(
    override val layoutId: Int = R.layout.fragment_main,
) : BaseFragment<FragmentMainBinding, Contract.View, MvpPresenter<Contract.View>>(),Contract.View,
 OnFilmClickListener {

    override val presenter : MainPresenter by inject()
    private var filmsList: List<Film> = listOf()

    private var adapter = MovieAdapter(this)
    private var adapterMovie: FilmsAdapter? = null

    private val analytics by inject<FirebaseAnalytics>()
    private val sharePref by inject<SharedPreferencesHelper>()
    private var bundleEx: Bundle = bundleOf()
    private var statusApp: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (sharePref.firstOpen==0 && sharePref.secondOpen==0) {
            sharePref.secondOpen = 1
            sharePref.lastGenresName = null
            sharePref.lastFilmList = listOf()
            sharePref.lastGenresPos = -1
        }

        presenter.checkConnection()

        if (sharePref.secondOpen==0) {
            presenter.firstOpen()
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.title_main)
        baseProgressBar = binding.progressBar
        adapter = MovieAdapter(this)
        binding.mainRecycler.adapter = adapter
        binding.mainRecycler.apply {
            adapter = adapter
        }
        presenter.getFilms()
        adapterMovie = FilmsAdapter(this)
        onGenrePicked()
        getLastSherPref()
        Log.w("Lifecycle","onViewCreated /${filmsList.size}/$bundleEx ")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        presenter.getFilms()
        getLastSherPref()
    }

    override fun showOnBoarding() {
        if (sharePref.firstOpen == 0 && sharePref.secondOpen ==0) {
            AlertDialog
                .Builder(requireContext())
                .setMessage(R.string.onboarding_message)
                .setIcon(R.drawable.logo_app_background)
                .setPositiveButton(getString(R.string.lets_go)) {
                        dialog, id ->  dialog.cancel()
                }
                .create()
                .show()
        }
        Log.w("firstOpen","showOnBoarding /${sharePref.firstOpen}")
    }

    override fun showAlertConnect() {
        AlertDialogFragment().show(
            childFragmentManager, AlertDialogFragment.TAG
        )
        baseProgressBar?.visibility = View.INVISIBLE
    }

    override fun showGenres(genres: List<Genre>) {
        adapter.data = genres
        listGenre = genres
    }

    override fun showMoviesByGenres(genre: String, film: List<Film>) {
        analytics.choiseGenre("Выбран жанр",genre)
        if (film.isNotEmpty()) {
            adapter.filmList = film
            filmsList = film
            sharePref.lastFilmList = filmsList
        } else if (sharePref.lastFilmList.isNotEmpty()) {
            adapter.filmList = sharePref.lastFilmList
            filmsList = sharePref.lastFilmList
        }
    }

    override fun firstOpen(count: Int) {
        sharePref.firstOpen = count
    }

    override fun showLoader() {
        baseProgressBar?.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        baseProgressBar?.visibility = View.INVISIBLE
    }

    override fun showError(error: String) {
        super.showError(error)
            AlertDialogFragment().show(
                childFragmentManager, AlertDialogFragment.TAG
            )
    }

    override fun networkStatus(status: Boolean) {
        if (!status) {
            AlertDialogFragment().show(
                childFragmentManager, AlertDialogFragment.TAG
            )
        }
        if (status) {
            presenter.getFilms()
            getLastSherPref()
        }
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
        bundleEx = bundle
        getOrientation(bundle,options)

    }

    private fun getLastSherPref(){
        sharePref.lastGenresName?.let { presenter.showMoviesByGenres(it) }
    }

    private fun onGenrePicked() {
        adapter.onGenreItemClick = { _, pos ->
            Timber.tag("onGenrePicked").d("${pos}" + "/" + listGenre[pos - 1])
            listGenre.forEachIndexed { index, genre ->
                when {
                    (index != pos - 1) -> {
                        genre.isChecked = false
                    }
                    (index == pos - 1 && genre.isChecked) -> {
                        genre.isChecked = false
                        sharePref.lastGenresPos = -1
                        sharePref.flagClick = false
                        sharePref.lastGenresName = null
                        presenter.getFilms()
                    }
                    (index == pos - 1 && !genre.isChecked) -> {
                        genre.isChecked = true
                        sharePref.flagClick = true
                        sharePref.lastGenresPos = pos
                        sharePref.lastGenresName = listGenre[pos - 1].name
                        presenter.showMoviesByGenres(listGenre[pos - 1].name)
                    }
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

    override fun showFilms(films: List<Film>) {
        if (sharePref.lastGenresName == null) {
            sharePref.flagClick = false
            adapter.filmList = films
            filmsList = films
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.checkConnection()
    }
}