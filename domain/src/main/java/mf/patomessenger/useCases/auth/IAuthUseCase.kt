package mf.patomessenger.useCases.auth

import mf.patomessenger.model.LoggedInUser
import mf.patomessenger.model.Result

interface IAuthUseCase {
    suspend fun login(username: String, password: String): Result<LoggedInUser>
    suspend fun register(userName: String, password: String): Result<LoggedInUser>
    suspend fun signOut()
    fun getCurrentUser(): LoggedInUser?
}
