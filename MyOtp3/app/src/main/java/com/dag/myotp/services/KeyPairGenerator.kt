package com.dag.myotp.services

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import javax.crypto.Cipher


@RequiresApi(Build.VERSION_CODES.M)
fun generateKeyPair(): KeyPair {
    val keyPairGenerator: KeyPairGenerator

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        // For Android M (API 23) and higher
        keyPairGenerator = KeyPairGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore"
        )
        keyPairGenerator.initialize(
            KeyGenParameterSpec.Builder(
                "myKeyAlias",
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .build()
        )
    } else {
        // For older Android versions
        keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048, SecureRandom())
    }

    return keyPairGenerator.generateKeyPair()
}


fun encryptData(publicKey: PublicKey, data: ByteArray): ByteArray {
    val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
    cipher.init(Cipher.ENCRYPT_MODE, publicKey)
    return cipher.doFinal(data)
}

fun decryptData(privateKey: PrivateKey, encryptedData: ByteArray): ByteArray {
    val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
    cipher.init(Cipher.DECRYPT_MODE, privateKey)
    return cipher.doFinal(encryptedData)
}