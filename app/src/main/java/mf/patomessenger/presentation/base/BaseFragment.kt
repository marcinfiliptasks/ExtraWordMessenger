package mf.patomessenger.presentation.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import mf.patomessenger.presentation.main.MainViewModel
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected abstract val viewModel: BaseViewModel

    protected val mainViewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    protected val navController by lazy {
        findNavController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBaseObservers()
    }

    private fun setupBaseObservers() {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let { showErrorSnackbar(it) }
        })
    }

    private fun showErrorSnackbar(message: String) =
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }

    protected fun navigateTo(direction: NavDirections) = navController.navigate(direction)

}