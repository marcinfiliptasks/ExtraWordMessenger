package mf.patomessenger.presentation.auth

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerAppCompatActivity
import mf.patomessenger.R
import kotlin.reflect.KFunction0

class AuthActivity : DaggerAppCompatActivity() {

    private var backPressAction: KFunction0<Boolean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupDirectionsListener()
    }

    private fun setupDirectionsListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.loginFragment ->{
                    supportActionBar?.show()
                }
                R.id.splashScreenFragment ->{
                    supportActionBar?.hide()
                }
            }
        }
    }

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.activity_login_host) as NavHostFragment)
            .findNavController()
    }

    override fun onBackPressed() {
        finish()
    }
}

