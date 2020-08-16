package mf.patomessenger.presentation.main.messages

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_message_received.view.*
import kotlinx.android.synthetic.main.item_message_sent.view.*
import mf.patomessenger.R
import mf.patomessenger.model.MessageModel
import mf.patomessenger.presentation.base.BaseAdapter

class MessagesAdapter(
    override val data: List<MessageModel>,
    private val userName: String
) : BaseAdapter<MessageModel>() {

    companion object {
        private const val RECEIVED_MESSAGE_TYPE = 0
        private const val SENT_MESSAGE_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == RECEIVED_MESSAGE_TYPE){
            ReceivedMessagesHolder(
                inflate(parent, R.layout.item_message_received)
            )
        }
        else {
            SentMessagesHolder(
                inflate(parent, R.layout.item_message_sent)
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ReceivedMessagesHolder -> holder.bind(data[position])
            is SentMessagesHolder -> holder.bind(data[position])
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (data[position].senderName == userName) SENT_MESSAGE_TYPE else RECEIVED_MESSAGE_TYPE


    class SentMessagesHolder(
        v: View
    ) : RecyclerView.ViewHolder(v) {
        fun bind(
            item: MessageModel
        ) {
            itemView.apply {
                item_message_sent_user_name.text = item.senderName
                item_message_sent_text.text = item.message
                item_message_sent_date_label.text = item.sendDate.toString()
            }
        }
    }

    class ReceivedMessagesHolder(
        v: View
    ) : RecyclerView.ViewHolder(v) {
        fun bind(
            item: MessageModel
        ) {
            itemView.apply {
                item_message_received_other_user_name.text = item.senderName
                item_message_received_text.text = item.message
                item_message_received_date_label.text = item.sendDate.toString()
            }
        }
    }
}