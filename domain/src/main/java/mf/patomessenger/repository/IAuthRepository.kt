package mf.patomessenger.repository

import mf.patomessenger.model.Result
import mf.patomessenger.model.LoggedInUser

interface IAuthRepository {
    suspend fun logout()
    suspend fun login(username: String, password: String): Result<LoggedInUser>
    suspend fun register(username: String, password: String): Result<LoggedInUser>
    fun getCurrentUser(): LoggedInUser?
}