package mf.patomessenger.presentation.main.contacts

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_contact.view.*
import mf.patomessenger.R
import mf.patomessenger.model.ContactModel
import mf.patomessenger.presentation.base.BaseAdapter

class ContactsAdapter(
    override val data: List<ContactModel>
) : BaseAdapter<ContactModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ConversationHolder(inflate(parent, R.layout.item_contact))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ConversationHolder) holder.bind(data[position])
    }

    class ConversationHolder(
        v: View
    ) : RecyclerView.ViewHolder(v) {
        fun bind(
            item: ContactModel
        ) {
            itemView.apply {
                item_contact_name.text = item.name
            }
        }
    }
}