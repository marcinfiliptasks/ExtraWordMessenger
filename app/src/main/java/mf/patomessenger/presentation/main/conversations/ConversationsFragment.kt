package mf.patomessenger.presentation.main.conversations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_conversations.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import mf.patomessenger.R
import mf.patomessenger.model.ConversationModel
import mf.patomessenger.presentation.base.BaseFragment

@FlowPreview
@ExperimentalCoroutinesApi
class ConversationsFragment : BaseFragment() {

    private val adapter by lazy {
        ConversationAdapter(
            ArrayList(),
            ::onCLick,
            mainViewModel.getCurrentUser()?.displayName.orEmpty()
        )
    }

    override val viewModel: ConversationsViewModel by activityViewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_conversations, container, false)


    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupObservers()
    }

    private fun setupRecycler() {
        fragment_conversations_rv.layoutManager = LinearLayoutManager(context)
        fragment_conversations_rv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.conversations.observe(viewLifecycleOwner, Observer {
            setAdapterState(it)
        })
    }

    private fun setAdapterState(items: List<ConversationModel>) {
        adapter.loadData(items)
        if (items.isEmpty()) {
            fragment_conversations_rv.visibility = View.GONE
            fragment_conversations_message.visibility = View.VISIBLE
        } else {
            fragment_conversations_rv.visibility = View.VISIBLE
            fragment_conversations_message.visibility = View.GONE
        }
    }

    private fun onCLick(conversation: ConversationModel) {
        navigateTo(
            ConversationsFragmentDirections.actionConversationsFragmentToMessagesFragment(
                conversation.id,
                mainViewModel.getCurrentUser()?.displayName.orEmpty().let {
                    conversation.users
                        .filter { user -> !user.equals(it, true) }
                        .toTypedArray()
                }
            )
        )
    }
}