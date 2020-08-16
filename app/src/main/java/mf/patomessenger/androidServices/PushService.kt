package mf.patomessenger.androidServices

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.android.AndroidInjection
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import mf.patomessenger.presentation.base.showNotification
import mf.patomessenger.useCases.auth.IAuthUseCase
import mf.patomessenger.useCases.conversations.IConversationsUseCase
import javax.inject.Inject

class PushService : FirebaseMessagingService() {
    companion object {
        var selectedConversation = ""
    }

    @Inject
    lateinit var conversationsUseCase: IConversationsUseCase

    @Inject
    lateinit var authUseCase: IAuthUseCase

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        if (authUseCase.getCurrentUser() != null) {
            ioContext.launch {
                conversationsUseCase.updateToken(token)
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.notification?.title != authUseCase.getCurrentUser()?.displayName
            && (selectedConversation.isEmpty() || message.from?.contains(selectedConversation) == false)
        ) {
            message.showNotification(this)
        }

    }

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }


    private val exceptionHandler =
        CoroutineExceptionHandler { _, _ ->
            //doNothing
        }

    private val ioContext = CoroutineScope(IO) + exceptionHandler
    private var job: Job? = null

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }

}