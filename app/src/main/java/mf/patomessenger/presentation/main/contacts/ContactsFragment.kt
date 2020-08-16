package mf.patomessenger.presentation.main.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_contacts.*
import mf.patomessenger.R
import mf.patomessenger.model.ContactModel
import mf.patomessenger.presentation.base.BaseFragment

class ContactsFragment : BaseFragment() {

    override val viewModel: ContactsViewModel by activityViewModels {
        viewModelFactory
    }

    private val adapter by lazy {
        ContactsAdapter(ArrayList())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_contacts, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupObservers()
    }

    private fun setupRecycler() {
        fragment_contacts_rv.layoutManager = LinearLayoutManager(context)
        fragment_contacts_rv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.contacts.observe(viewLifecycleOwner, Observer {
            it?.let { items -> setAdapterState(items) }
        })
    }

    private fun setAdapterState(items: List<ContactModel>) {
        with(adapter) {
            loadData(items)
            if (items.isEmpty()) {
                fragment_contacts_rv.visibility = View.GONE
                fragment_contacts_message.visibility = View.VISIBLE
            } else {
                fragment_contacts_rv.visibility = View.VISIBLE
                fragment_contacts_message.visibility = View.GONE
            }
        }
    }
}