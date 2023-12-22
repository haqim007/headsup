package dev.haqim.headsup.ui.newsdetail

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import dev.haqim.headsup.R
import dev.haqim.headsup.ui.component.PlainMessage

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun NewsDetailScreen(
    modifier: Modifier = Modifier,
    id: Int,
    isFromSaved: Boolean,
    viewModel: NewsDetailViewModel = hiltViewModel(),
){
    val uiState by viewModel.state.collectAsState()
    viewModel.getArticle(id, isFromSaved)
    uiState.article?.url?.let {url ->
        val webViewState = rememberWebViewState(url = url)
        WebView(
            modifier = modifier,
            state = webViewState,
            captureBackPresses = true,
            onCreated = { it: WebView ->
                it.settings.javaScriptEnabled = true
            }
        )
    } ?: run {
        PlainMessage(message = stringResource(R.string.no_data_found))
    }
}