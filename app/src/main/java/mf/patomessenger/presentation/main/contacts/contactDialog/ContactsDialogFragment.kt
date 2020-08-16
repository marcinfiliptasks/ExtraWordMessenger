package mf.patomessenger.presentation.main.contacts.contactDialog

import mf.patomessenger.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.dialog_add_contatcs.*
import mf.patomessenger.presentation.base.afterTextChanged
import mf.patomessenger.presentation.stateModel.BasicFormState
import javax.inject.Inject

class ContactsDialogFragment : DaggerDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ContactsDialogViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = true
        return inflater.inflate(R.layout.dialog_add_contatcs, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setWindowAnimations(R.style.AppTheme_Slide)
        setupEvents()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.basicState.observe(viewLifecycleOwner, Observer {
            processState(it)
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let { showErrorSnackbar(it) }
        })
    }

    private fun processState(state: BasicFormState) {
        if (state.allDone) {
            dismiss()
        } else {
            dialog_confirm.isEnabled = !state.uiBlocked && state.isDataValid
            dialog_add_contacts_back.isEnabled = !state.uiBlocked
            if (state.uiBlocked) {
                contacts_dialog_text_input.visibility = View.GONE
                contacts_dialog_message.visibility = View.VISIBLE
                contacts_dialog_progressbar.visibility = View.VISIBLE
            } else {
                contacts_dialog_text_input.visibility = View.VISIBLE
                contacts_dialog_message.visibility = View.GONE
                contacts_dialog_progressbar.visibility = View.GONE
            }
            contacts_dialog_text_input.error =
                if (!state.isDataValid) getString(R.string.invalid_mail) else null

        }
    }


    private fun setupEvents() {
        dialog_confirm.setOnClickListener {
                viewModel.addContact(contacts_dialog_text_input.text.toString())
        }
        contacts_dialog_text_input.afterTextChanged {
            viewModel.checkMail(it)
        }
        dialog_add_contacts_back.setOnClickListener {
            dismiss()
        }
    }

    override fun onPause() {
        dialog?.window?.setWindowAnimations(0)
        super.onPause()
    }

    private fun showErrorSnackbar(message: String) =
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }

    override fun dismiss() {
        dismissAllowingStateLoss()
    }
}



