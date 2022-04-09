package ui.smartpro.sequenia.presentation.base

import androidx.annotation.CallSuper
import ui.smartpro.sequenia.presentation.common.AppState

abstract class BasePresenter<T : AppState,V : MvpView>: MvpPresenter<T, V> {

    protected var view: V? = null
        private set

    @CallSuper
    override fun attach(view: V) {
        this.view = view
    }

    @CallSuper
    override fun detach(view: V?) {
        this.view = null
    }

}