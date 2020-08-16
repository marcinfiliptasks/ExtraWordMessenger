package mf.patomessenger.presentation.main.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import mf.patomessenger.R
import mf.patomessenger.model.MessageModel
import mf.patomessenger.presentation.base.BaseFragment
import mf.patomessenger.presentation.main.MainActivity
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener


@ExperimentalCoroutinesApi
@FlowPreview
class MessagesFragment : BaseFragment() {
    override val viewModel: MessagesViewModel by viewModels {
        viewModelFactory
    }
    private val adapter by lazy {
        MessagesAdapter(ArrayList(), mainViewModel.getCurrentUser()?.displayName.orEmpty())
    }
    private val arguments by navArgs<MessagesFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_messages, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setConversationId(arguments.conversationId)
        fragment_messages_input_layout.setEndIconOnClickListener { sendMessage() }
        setupRecycler()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.messages.observe(viewLifecycleOwner, Observer {
            setAdapterState(it)
        })

        setEventListener(
            activity ?: return,
            viewLifecycleOwner,
            object : KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {
                    scrollToBottom()
                }
            })
    }

    private fun setAdapterState(items: List<MessageModel>) {
        adapter.loadData(items)
        if (items.isEmpty()) {
            fragment_messages_rv.visibility = View.GONE
            fragment_messages_empty_message.visibility = View.VISIBLE
        } else {
            fragment_messages_rv.visibility = View.VISIBLE
            fragment_messages_empty_message.visibility = View.GONE
            scrollToBottom()
        }
    }

    private fun setupRecycler() {
        fragment_messages_rv.layoutManager = LinearLayoutManager(context)
        fragment_messages_rv.adapter = adapter
    }

    private fun sendMessage() {
        viewModel.sendMessage(arguments.conversationId, fragment_messages_input.text.toString())
        fragment_messages_input.text?.clear()
    }

    override fun onPause() {
        super.onPause()
        viewModel.setPushInfo("")
    }

    override fun onResume() {
        (activity as MainActivity).main_nav_menu.visibility = View.GONE
        super.onResume()
        viewModel.setPushInfo(arguments.conversationId)
    }

    fun scrollToBottom() =  fragment_messages_rv.scrollToPosition(adapter.itemCount-1)
}


