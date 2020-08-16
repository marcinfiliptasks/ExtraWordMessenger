package mf.patomessenger.presentation.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import mf.patomessenger.R
import mf.patomessenger.model.LoginResult
import mf.patomessenger.presentation.base.BaseFragment
import mf.patomessenger.presentation.base.afterTextChanged
import mf.patomessenger.presentation.dialog.AlertDialog
import mf.patomessenger.presentation.stateModel.LoginFormState

class AuthFragment : BaseFragment() {
    //region Setup
    override val viewModel: AuthViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupEvents()
    }

    private fun setupObservers() {
        viewModel.loginFormState.observe(viewLifecycleOwner, Observer { state ->
            funProcessState(state)
        })

        viewModel.loginResult.observe(viewLifecycleOwner, Observer { loginResult ->
            processResult(loginResult)
        })
    }

    private fun setupEvents() {
        username.afterTextChanged {
            processLoginDataChanged()
        }

        password.apply {
            afterTextChanged {
                processLoginDataChanged()
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        viewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                viewModel.login(username.text.toString(), password.text.toString())
            }
        }

        fragment_login_register.setOnClickListener {
            activity?.supportFragmentManager?.let {
                AlertDialog().display(
                    it, getString(R.string.singn_up),
                    getString(R.string.sign_up_q),
                    true,
                    tag.orEmpty(),
                    ::register
                )
            }
        }
    }
    //endregion

    private fun processLoginDataChanged() =
        viewModel.loginDataChanged(
            username.text.toString(),
            password.text.toString()
        )


    private fun processResult(loginResult: LoginResult?) =
        loginResult?.let {
            loading.visibility = View.GONE

            loginResult.success?.let {
                processLogin()
            }
        }


    private fun funProcessState(state: LoginFormState?) =
        state?.let {
            login.isEnabled = state.isDataValid
            fragment_login_register.isEnabled = state.isDataValid
            fragment_login_mail.error = state.usernameError?.let { getString(it) }
            fragment_login_pass.error = state.passwordError?.let { getString(it) }
            loading.visibility = if (state.uiBlocked) View.VISIBLE else View.GONE
            fragment_login_mail.isEnabled = !state.uiBlocked
            fragment_login_pass.isEnabled = !state.uiBlocked
            context?.let {
                fragment_login_register.setTextColor(
                    ContextCompat.getColor(
                        it,
                        if (!state.isDataValid) R.color.material_on_surface_emphasis_medium else R.color.colorPrimary
                    )
                )
            }
        }

    private fun processLogin() = CoroutineScope(Main).launch {
        navigateTo(AuthFragmentDirections.actionLoginFragmentToMainActivity())
        activity?.finish()
    }

    private fun register() {
        viewModel.register(username.text.toString(), password.text.toString())
    }
}