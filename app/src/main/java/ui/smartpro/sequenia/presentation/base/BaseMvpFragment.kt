package ui.smartpro.sequenia.presentation.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil.inflate
import androidx.viewbinding.ViewBinding
import moxy.MvpAppCompatFragment
import moxy.MvpPresenter
import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film

abstract class BaseMvpFragment<Binding: ViewBinding,V : FilmView, P : MvpPresenter<V>>
    : MvpAppCompatFragment(), FilmView {

    abstract val presenter: P
    var baseProgressBar: ProgressBar? = null
    abstract override fun showGenres(genres: List<Genre>)
    abstract override fun showFilms(films: List<Film>)
    abstract override fun showMoviesByGenres(genre: String, film: List<Film>)

    protected abstract val layoutId: Int
    protected lateinit var binding: Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter.attachView(this as V)
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detachView(this as V)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("UNCHECKED_CAST")
        presenter.attachView(this as V)
    }
//
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        @Suppress("UNCHECKED_CAST")
        presenter.attachView(this as V)
    }
//
    override fun onStart() {
        super.onStart()
        @Suppress("UNCHECKED_CAST")
        presenter.attachView(this as V)
    }
//
    override fun onDestroyView() {
        super.onDestroyView()
        @Suppress("UNCHECKED_CAST")
        presenter.detachView(this as V)
    }
//
    override fun onResume() {
        super.onResume()
        @Suppress("UNCHECKED_CAST")
        presenter.attachView(this as V)
    }
//
//    override fun onPause() {
//        super.onPause()
//        @Suppress("UNCHECKED_CAST")
//        presenter.detachView(this as V)
//    }

    override fun showError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoader() {
        baseProgressBar?.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        baseProgressBar?.visibility = View.INVISIBLE
    }
}