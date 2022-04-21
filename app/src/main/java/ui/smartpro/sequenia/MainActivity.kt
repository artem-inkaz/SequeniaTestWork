package ui.smartpro.sequenia

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import ui.smartpro.sequenia.databinding.ActivityMainBinding
import ui.smartpro.sequenia.presentation.base.BaseActivity
import ui.smartpro.sequenia.presentation.base.BaseView

class MainActivity : BaseActivity<BaseView>() {

    lateinit var mToolbar: Toolbar
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mToolbar = binding.toolbar
        setSupportActionBar(mToolbar)

        mToolbar.title = getString(R.string.title_main)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = getString(R.string.title_main)
        return true
    }
}