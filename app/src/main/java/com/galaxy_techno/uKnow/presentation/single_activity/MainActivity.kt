package com.galaxy_techno.uKnow.presentation.single_activity

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.databinding.ActivityMainBinding

import com.galaxy_techno.uKnow.presentation.base.BaseActivity
import com.galaxy_techno.uKnow.presentation.extension.setStatusBarColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
    }
    private val navController: NavController by lazy {
        navHostFragment.navController
    }
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(this) }

    private val viewModel: MainViewModel by viewModels()

    override fun initialize() {
        super.initialize()
        setStatusBarColor()
    }
    fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        if (isDarkTheme(this))
            this.setStatusBarColor(ContextCompat.getColor(this, R.color.black))
        else
            this.setStatusBarColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun isDarkTheme(activity: Activity): Boolean {
        return activity.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    override fun observe() {
        super.observe()
        viewModel.authState.observe(this) {
            setupNavigation(it)
        }
    }

    private fun setupNavigation(isLoggedIn: Boolean) {
        setSupportActionBar(binding.authToolbar)

        val navGraph = navController.navInflater.inflate(
            R.navigation.main_navigation
        )
       // navGraph.startDestination = if (isLoggedIn) R.id.dest_top_home else R.id.auth_navigation
        navGraph.startDestination = if( isLoggedIn) R.id.auth_navigation else R.id.dest_top_home
        navController.graph = navGraph
        appBarConfiguration = AppBarConfiguration(navGraph)


        binding.btnNavView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dest_top_home,
                R.id.dest_top_chat,
                R.id.dest_top_feeds,
                R.id.dest_top_profile_setting
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun shouldShowToolbar(flag: Boolean) {
        if (flag) {
            binding.authToolbar.visibility = View.VISIBLE
        } else binding.authToolbar.visibility = View.GONE
    }

    fun shouldShowBottomNavigation(flag: Boolean) {
        if (flag) {
            binding.btnNavView.visibility = View.VISIBLE
        }else binding.btnNavView.visibility = View.GONE
    }

    fun showLoadingDialog(text: String) {
        loadingDialog.apply {
            setMessage(text)
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            show()
        }
    }

    fun hideLoadingDialog() {
        loadingDialog.hide()
    }


}

