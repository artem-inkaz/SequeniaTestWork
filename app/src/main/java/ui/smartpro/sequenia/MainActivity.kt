package ui.smartpro.sequenia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import ui.smartpro.sequenia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mToolbar: Toolbar
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mToolbar = binding.toolbar!!
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
}