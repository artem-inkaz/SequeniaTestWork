package ui.smartpro.sequenia.presentation.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import moxy.MvpAppCompatActivity
import moxy.MvpPresenter
import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film

abstract class BaseMvpActivity<V : BaseView> :
    MvpAppCompatActivity() {
}