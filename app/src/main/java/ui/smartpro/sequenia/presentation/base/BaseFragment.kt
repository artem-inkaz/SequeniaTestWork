package ui.smartpro.sequenia.presentation.base

import androidx.viewbinding.ViewBinding
import ui.smartpro.sequenia.presentation.common.AppState

abstract class BaseFragment<Binding: ViewBinding, T : AppState, V : MvpView, P : MvpPresenter<T, V>> :
    BaseMvpFragment<Binding,T, V, P>() {

}