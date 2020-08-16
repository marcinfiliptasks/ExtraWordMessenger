package mf.patomessenger.model

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val success: String? = null,
        val error: Int? = null
)