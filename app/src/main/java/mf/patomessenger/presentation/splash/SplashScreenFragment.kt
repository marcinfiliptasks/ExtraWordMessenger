package mf.patomessenger.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import mf.patomessenger.R
import mf.patomessenger.presentation.base.BaseFragment

class SplashScreenFragment : BaseFragment() {

    override val viewModel: SplashScreenViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_splash, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.authState.observe(viewLifecycleOwner, Observer { isLoggedIn ->
            if (isLoggedIn) {
                navigateTo(
                    SplashScreenFragmentDirections.actionSplashScreenFragmentToMainActivity()
                )
                activity?.finish()
            } else {
                navigateTo(
                    SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment()
                )
            }
        })
    }


}