package com.example.mybenedictapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.mybenedictapp.R
import com.example.mybenedictapp.ui.theme.LoadingTransparentBackground
import com.example.mybenedictapp.ui.theme.Secondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val PROGRESSBAR_FOCUS_DELAY = 200L

@Composable
fun FullScreenProgressIndicator(backgroundColor: Color = LoadingTransparentBackground) {
    val focusRequester = FocusRequester()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .focusRequester(focusRequester)
                .focusable(enabled = true)
                .semantics { contentDescription = context.getString(R.string.loading) },
            color = Secondary
        )
    }

    SideEffect {
        coroutineScope.launch {
            delay(PROGRESSBAR_FOCUS_DELAY)
            focusRequester.requestFocus()
        }
    }
}