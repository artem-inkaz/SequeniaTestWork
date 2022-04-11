package ui.smartpro.sequenia.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import moxy.MvpPresenter
import org.koin.android.ext.android.inject
import timber.log.Timber
import ui.smartpro.sequenia.R
import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.databinding.FragmentMainBinding
import ui.smartpro.sequenia.debug.FirebaseAnalytics
import ui.smartpro.sequenia.presentation.base.BaseFragment
import ui.smartpro.sequenia.presentation.contract.Contract
import ui.smartpro.sequenia.presentation.main.MainPresenter
import ui.smartpro.sequenia.presentation.main.adapter.MovieAdapter
import ui.smartpro.sequenia.presentation.main.filmadapter.FilmsAdapter
import ui.smartpro.sequenia.presentation.main.filmadapter.OnFilmClickListener
import ui.smartpro.sequenia.presentation.movie_details.FilmDetailFragment.Companion.BUNDLE_EXTRA
import ui.smartpro.sequenia.utils.CommonConstants.listGenre
import ui.smartpro.sequenia.utils.SharedPreferencesHelper

class MainFragment(
    override val layoutId: Int = R.layout.fragment_main,
) : BaseFragment<FragmentMainBinding, Contract.View, MvpPresenter<Contract.View>>(),Contract.View,
 OnFilmClickListener {

//    override val presenter: Contract.Presenter by inject()
    override val presenter : MainPresenter by inject()
//    override val presenter by moxyPresenter { MainPresenter() }
    private var filmsList: List<Film> = listOf()

    private var adapter = MovieAdapter(this)
    private var adapterMovie: FilmsAdapter? = null

    private val analytics by inject<FirebaseAnalytics>()
    private val sharePref by inject<SharedPreferencesHelper>()
    private var bundleEx: Bundle = bundleOf()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if (savedInstanceState == null) {
//            sharePref.lastGenresName = null
//            sharePref.lastFilmList = listOf()
//            sharePref.lastGenresPos = -1
//        }

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Главная"
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

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        if (!filmsList.isNullOrEmpty())
//        outState.putParcelable("last_list_film", BaseParcelable(filmsList))
//        Log.w("Lifecycle","onSaveInstanceState /${filmsList.size}/$outState ")
//    }
//
//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        val any = arguments?.getParcelable<BaseParcelable>("last_list_film")?.value
//        if (any != null)
//        filmsList = any as List<Film>
//        Log.w("Lifecycle","onViewStateRestored /$any /${filmsList.size}")
//    }

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
        Log.w("Lifecycle","sharePref showMoviesByGenres /${sharePref.lastFilmList.size} /${filmsList.size}")
    }

    override fun showLoader() {
        baseProgressBar?.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        baseProgressBar?.visibility = View.INVISIBLE
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
//            presenter.showMoviesByGenres(listGenre[pos - 1].name)
            Timber.tag("onGenrePicked").d("${pos}" + "/" + listGenre[pos - 1])
//            sharePref.lastGenresName = listGenre[pos - 1].name
//            sharePref.lastGenresPos = pos
            listGenre.forEachIndexed { index, genre ->
                when {
                    (index != pos - 1) -> {
                        genre.isChecked = false
//                        sharePref.flagClick = false
                        Log.w("positions","(index != pos - 1) ${genre.isChecked}/${index}/${pos - 1}/${sharePref.lastGenresPos}/$${sharePref.flagClick}")
                    }
                    (index == pos - 1 && genre.isChecked) -> {
                        genre.isChecked = false
                        sharePref.lastGenresPos = -1
                        sharePref.flagClick = false
                        sharePref.lastGenresName = null
                        presenter.getFilms()
                        Log.w("positions","(index == pos - 1 && genre.isChecked) ${sharePref.lastGenresPos}/${sharePref.flagClick}")
                    }
                    (index == pos - 1 && !genre.isChecked) -> {
                        genre.isChecked = true
                        sharePref.flagClick = true
                        sharePref.lastGenresPos = pos
                        sharePref.lastGenresName = listGenre[pos - 1].name
                        presenter.showMoviesByGenres(listGenre[pos - 1].name)
                        Log.w("positions","(index == pos - 1 && !genre.isChecked) ${sharePref.lastGenresPos}/$${sharePref.flagClick}")
                    }
                }
                Log.w("positions","genre status ${genre.isChecked}")
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

    override fun onStart() {
        super.onStart()
        Log.w("Lifecycle","onStart /${filmsList.size} /$bundleEx")
    }

    override fun onResume() {
        super.onResume()
        Log.w("Lifecycle","onResume /${filmsList.size} /$bundleEx")
    }

    override fun onStop() {
        super.onStop()
        Log.w("Lifecycle","onStop /${filmsList.size} /$bundleEx")
    }

    override fun onPause() {
        super.onPause()
        Log.w("Lifecycle","onPause /$bundleEx /${filmsList.size}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.w("Lifecycle","onDestroyView /${filmsList.size} /$bundleEx")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.w("Lifecycle","onAttach /${filmsList.size} /$bundleEx")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w("Lifecycle","onDestroy /${filmsList.size} /$bundleEx")
    }
}