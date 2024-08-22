package com.dag.myotp

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.dag.myotp.data.register.RegisterRequest
import com.dag.myotp.services.ApiSource
import com.dag.myotp.services.Datastore
import com.dag.myotp.services.generateKeyPair
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltAndroidApp
class MyOtpApplication: Application() {

    @Inject
    lateinit var apiSource: ApiSource

    @Inject
    lateinit var datastore: Datastore

    val walletAddress = mutableStateOf("")

    companion object {
        lateinit var appInstance: MyOtpApplication
        var baseUrl = "https://api.dogukangun.de/"
    }

    private suspend fun saveKeypair(){
        val keypair = generateKeyPair()
        walletAddress.value = keypair.public.toString()
        datastore.save(Datastore.PRIVATE_KEY_PREF,keypair.private.toString())
        datastore.save(Datastore.PUBLIC_KEY_PREF,keypair.public.toString())
    }

    private suspend fun createPrivateKey(){
        datastore.read(Datastore.PUBLIC_KEY_PREF)
            .first { publicKey ->
                if (publicKey == null) {
                    saveKeypair()
                    false
                } else {
                    true
                }
            }
    }

    private fun createToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d(TAG, token)
            runBlocking {
                token?.let {
                    createPrivateKey()
                    apiSource.register(RegisterRequest(walletAddress.value,it))
                }
            }
        })
    }

    override fun onCreate() {
        FirebaseApp.initializeApp(applicationContext)
        super.onCreate()
        appInstance = this
        createToken()
    }


}