package mf.patomessenger.model

import java.sql.Timestamp

data class UserTokenData(
    val token: String,
    val editDate: Timestamp
)