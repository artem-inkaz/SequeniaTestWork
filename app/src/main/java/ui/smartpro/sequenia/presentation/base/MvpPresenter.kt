package ui.smartpro.sequenia.presentation.base

import ui.smartpro.sequenia.presentation.common.AppState

interface MvpPresenter<T : AppState, V : MvpView> {

    fun attach(view: V)

    fun detach(view: V?)
}