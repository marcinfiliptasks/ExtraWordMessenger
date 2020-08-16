package mf.patomessenger.model

import kotlin.collections.ArrayList

data class ConversationModel(
    val id: String = "",
    val lastActivity: Long = 0,
    val lastMessage: String = "",
    val lastMessageFrom: String = "",
    val readBy: ArrayList<String> = ArrayList(),
    val users: ArrayList<String> = ArrayList()
)