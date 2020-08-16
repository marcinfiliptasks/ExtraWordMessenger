package mf.patomessenger.useCases.auth

import mf.patomessenger.model.LoggedInUser
import mf.patomessenger.model.Result
import mf.patomessenger.repository.IAuthRepository
import javax.inject.Inject


class AuthUseCase @Inject constructor(
    private val _authRepository: IAuthRepository) :
    IAuthUseCase {

    override suspend fun login(username: String, password: String): Result<LoggedInUser> =
        _authRepository.login(username, password)

    override suspend fun register(userName: String, password: String) =
        _authRepository.register(userName, password)

    override suspend fun signOut() {
        _authRepository.logout()
    }


    override fun getCurrentUser(): LoggedInUser? = _authRepository.getCurrentUser()

}
