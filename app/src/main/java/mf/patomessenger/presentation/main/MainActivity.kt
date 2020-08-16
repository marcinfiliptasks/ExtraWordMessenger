package mf.patomessenger.presentation.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header.view.*
import mf.patomessenger.R
import mf.patomessenger.androidServices.PushService
import mf.patomessenger.presentation.dialog.AlertDialog
import mf.patomessenger.presentation.main.contacts.ContactsFragmentDirections
import mf.patomessenger.presentation.main.contacts.contactDialog.ContactsDialogFragment
import mf.patomessenger.presentation.main.conversations.ConversationsFragmentDirections
import javax.inject.Inject
import kotlin.reflect.KFunction0


class MainActivity : DaggerAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var backPressAction: KFunction0<Boolean>? = null

    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment)
            .findNavController()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupDrawer()
        setupObservers()
        setupEvents()
        PushService()
    }

    private fun setupDrawer() {
        with(
            ActionBarDrawerToggle(
                this, drawer_layout, toolbar, 0,
                0
            )
        )
        {
            drawer_layout.addDrawerListener(this)
            syncState()
        }
        nav_view.setNavigationItemSelectedListener(this)

        nav_view.getHeaderView(0).drawer_username.text =
            viewModel.getCurrentUser()?.displayName.orEmpty()
    }

    private fun setupEvents() {
        main_nav_menu.setOnNavigationItemSelectedListener {
            processBottomNavEvent(it)
            true
        }
        app_bar_extra_btn.setOnClickListener {
            ContactsDialogFragment().show(supportFragmentManager, null)
        }

    }

    private fun processBottomNavEvent(it: MenuItem) {
        if (it.isChecked) return
        when (it.itemId) {
            R.id.conversations_Fragment -> {
                navigateTo(
                    ContactsFragmentDirections
                        .actionContactsFragmentToConversationsFragment()
                )
            }
            R.id.contacts_Fragment -> {
                navigateTo(
                    ConversationsFragmentDirections
                        .actionConversationsFragmentToContactsFragment()
                )
            }
        }
    }

    private fun navigateTo(id: NavDirections) = navController.navigate(id)

    private fun setupObservers() {
        viewModel.mainState.observe(this, Observer {
            if (it.loggedOut) {
                finish()
            }
        })

        navController.addOnDestinationChangedListener { _, destination, arguments ->
            when (destination.id) {
                R.id.messagesFragment -> {
                    backPressAction = navController::navigateUp
                    toolbar_title.text = (arguments?.get("users") as Array<*>)
                        .joinToString(",")
                    main_nav_menu.visibility = View.GONE
                }
                R.id.conversationsFragment -> {
                    toolbar_title.text = getString(R.string.app_name)
                    backPressAction = null
                    main_nav_menu.visibility = View.VISIBLE
                    app_bar_extra_btn.visibility = View.GONE
                }
                R.id.contactsFragment -> {
                    app_bar_extra_btn.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onBackPressed() {
        backPressAction?.invoke()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout -> {
                AlertDialog().display(
                    supportFragmentManager,
                    getString(R.string.logout_q), getString(R.string.sign_out),
                    true,
                    localClassName,
                    ::signOut
                )
            }
        }
        return true
    }

    private fun signOut() {
        viewModel.signOut()
        viewModel.unsubscribeTopics()
    }
}
