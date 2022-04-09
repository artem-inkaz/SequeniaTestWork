package ui.smartpro.sequenia.presentation.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.presentation.common.AppState

abstract class BaseMvpFragment<Binding: ViewBinding,T : AppState, V : MvpView, P : MvpPresenter<T, V>>
    : Fragment(), MvpView {

    abstract val presenter: P

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("UNCHECKED_CAST")
        presenter.attach(this as V)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        @Suppress("UNCHECKED_CAST")
        presenter.detach(this as V)
    }

    override fun onStart() {
        super.onStart()
        @Suppress("UNCHECKED_CAST")
        presenter.detach(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        @Suppress("UNCHECKED_CAST")
        presenter.detach(this as V)
    }

    override fun onResume() {
        super.onResume()
        @Suppress("UNCHECKED_CAST")
        presenter.attach(this as V)
    }

    override fun onPause() {
        super.onPause()
        @Suppress("UNCHECKED_CAST")
        presenter.detach(this as V)
    }
}