package mf.patomessenger.model

import java.util.*

data class MessageModel(
    val senderId: String = "",
    val message: String = "",
    val conversationId: String = "",
    val sendDate: Date = Calendar.getInstance().time,
    val senderName: String = ""
)