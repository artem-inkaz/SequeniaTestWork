package ui.smartpro.sequenia.presentation.base

import androidx.viewbinding.ViewBinding
import moxy.MvpPresenter

abstract class BaseFragment<Binding: ViewBinding, V : FilmView, P : MvpPresenter<V>> :
    BaseMvpFragment<Binding,V, P>() {

}