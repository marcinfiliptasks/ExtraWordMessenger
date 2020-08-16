package mf.patomessenger.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import mf.patomessenger.model.LoggedInUser
import mf.patomessenger.model.Result
import mf.patomessenger.model.exceptions.UnauthorizedException
import java.lang.Exception
import javax.inject.Inject

class AuthRepository @Inject constructor(private val auth: FirebaseAuth) :
    IAuthRepository {

    private var user: LoggedInUser? = null
        get() {
            if (field == null) {
                field = auth.currentUser?.toLoggedInUser()
            }
            return field
        }

    override suspend fun logout() {
        auth.signOut()
        user = null
    }

    override suspend fun login(username: String, password: String): Result<LoggedInUser> = try {
        auth.signInWithEmailAndPassword(username, password).await().getResult()
    } catch (e: Throwable) {
        Result.Error(Exception(e.message))
    }

    override suspend fun register(username: String, password: String) = try {
        auth.createUserWithEmailAndPassword(username, password).await().getResult()
    } catch (ex: Throwable) {
        Result.Error(UnauthorizedException())
    }

    override fun getCurrentUser(): LoggedInUser? = user

    private fun FirebaseUser.toLoggedInUser(): LoggedInUser =
        LoggedInUser(uid, email.orEmpty())

    private fun AuthResult.getResult(): Result<LoggedInUser> =
        user?.let {
            Result.Success(LoggedInUser(it.uid, it.email.orEmpty()))
        } ?: Result.Error(UnauthorizedException())
}





