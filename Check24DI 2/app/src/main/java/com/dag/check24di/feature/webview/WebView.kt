package com.dag.check24di.feature.webview

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen() {
    val webViewState = rememberWebViewState(
        url = "https://m.check24.de/rechtliche-hinweise?deviceoutput=app"
    )
    WebView(
        state = webViewState,
        onCreated = { webView ->
            webView.settings.javaScriptEnabled = true
        }
    )
}