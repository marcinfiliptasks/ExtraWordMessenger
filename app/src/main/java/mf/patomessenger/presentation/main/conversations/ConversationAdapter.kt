package mf.patomessenger.presentation.main.conversations

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_conversation.view.*
import mf.patomessenger.R
import mf.patomessenger.model.ConversationModel
import mf.patomessenger.presentation.base.BaseAdapter
import java.sql.Timestamp
import kotlin.reflect.KFunction1

class ConversationAdapter(
    override val data: List<ConversationModel>,
    private val onClick: KFunction1<ConversationModel, Unit>,
    private val userName: String
) : BaseAdapter<ConversationModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ConversationHolder(inflate(parent, R.layout.item_conversation))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ConversationHolder) holder.bind(data[position], onClick, userName)
    }

    class ConversationHolder(
        v: View
    ) : RecyclerView.ViewHolder(v) {
        fun bind(
            item: ConversationModel,
            onClick: KFunction1<ConversationModel, Unit>,
            userName: String
        ) {
            itemView.apply {
                item_conversation_name.text = item.users.filter { it != userName }
                    .joinToString(separator = ",")

                item_conversation_content.text =
                    if (item.lastMessageFrom.isNotEmpty())
                        if (item.lastMessageFrom == userName)
                            context.getString(R.string.lastMessage, context.getString(R.string.you), item.lastMessage)
                        else
                            item.lastMessage
                    else context.getString(R.string.no_messages)
                item_conversation_time.text = Timestamp(item.lastActivity).toString()
                setOnClickListener { onClick(item) }
            }

        }
    }
}