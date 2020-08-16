package mf.patomessenger.presentation.main.settings

import androidx.fragment.app.viewModels
import mf.patomessenger.presentation.base.BaseFragment

class SettingsFragment : BaseFragment() {

    override val viewModel: SettingsViewModel by viewModels {
        viewModelFactory
    }

}