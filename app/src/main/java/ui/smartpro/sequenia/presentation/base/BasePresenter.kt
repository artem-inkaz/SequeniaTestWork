package ui.smartpro.sequenia.presentation.base

import moxy.MvpPresenter

abstract class BasePresenter<T : BaseView>: MvpPresenter<T>() {

    protected var view: T? = null
        private set

    override fun detachView(view: T) {
        super.detachView(view)
        this.view = null
    }

    override fun attachView(view: T) {
        super.attachView(view)
        this.view = view
    }

    fun showError(error: String) {
        viewState?.showError(error)
    }

    fun showMessage(message: String){
        viewState?.showMessage(message)
    }

    fun onLoading(){
        viewState?.showLoader()
    }

    fun onLoadingFinished(){
        viewState?.hideLoader()
    }

}