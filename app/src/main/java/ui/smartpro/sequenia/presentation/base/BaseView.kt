package ui.smartpro.sequenia.presentation.base

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.SingleState

interface BaseView:MvpView {
    @SingleState
    fun showError(error: String)
    @SingleState
    fun showMessage(message: String)
    @AddToEndSingle
    fun showLoader()
    @AddToEndSingle
    fun hideLoader()
}