package ui.smartpro.sequenia

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import org.koin.android.ext.android.inject
import ui.smartpro.sequenia.databinding.ActivityMainBinding
import ui.smartpro.sequenia.presentation.base.BaseActivity
import ui.smartpro.sequenia.presentation.base.BaseView
import ui.smartpro.sequenia.utils.SharedPreferencesHelper

class MainActivity : BaseActivity<BaseView>() {

    lateinit var mToolbar: Toolbar
    lateinit var binding: ActivityMainBinding
    private val sharePref by inject<SharedPreferencesHelper>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mToolbar = binding.toolbar
        setSupportActionBar(mToolbar)

        mToolbar.title = "Главная"
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = "Главная"
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w("Lifecycle","onDestroy Activity ")
    }
}