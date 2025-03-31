package com.example.mybenedictapp.ui.screens

import android.os.SystemClock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mybenedictapp.R
import com.example.mybenedictapp.core.network.ErrorType
import com.example.mybenedictapp.ui.theme.Primary
import com.example.mybenedictapp.ui.theme.TextRegular

const val DEBOUNCE_DELAY = 500L

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    onConfirmationAction: () -> Unit = {},
    errorType: ErrorType? = ErrorType.GeneralError
) {
    when (errorType) {
        ErrorType.GeneralError -> {
            MovieDialog(
                title = {
                    Text(
                        text = stringResource(id = R.string.error_alert_title),
                        color = TextRegular,
                    )
                },
                text = stringResource(id = R.string.error_alert_text),
                confirmButtonText = R.string.ok,
                onConfirmAction = onConfirmationAction
            )
        }
        else -> {}
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MovieDialog(
    title: @Composable (() -> Unit)?,
    text: String,
    confirmButtonText: Int,
    onConfirmAction: () -> Unit = {},
    dismissButtonText: Int? = null,
    testTag: String = "",
    onDismissAction: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    AlertDialog(
        modifier = Modifier
            .semantics { testTagsAsResourceId = true }
            .testTag(testTag),
        onDismissRequest = onDismissRequest,
        confirmButton = {
            AlertButton(
                confirmButtonText,
                onConfirmAction
            )
        },
        dismissButton = {
            dismissButtonText?.let {
                AlertButton(
                    text = dismissButtonText,
                    onAction = onDismissAction
                )
            }
        },
        title = title,
        text = {
            Text(
                text = text,
                color = TextRegular,
            )
        },
    )
}


@Composable
fun AlertButton(text: Int, onAction: () -> Unit = {}) {
    TextButton(onClick = debounced { onAction() }) {
        Text(
            text = stringResource(id = text),
            style = TextStyle(
                color = Primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
    }
}


/**
 * https://gist.github.com/leonardoaramaki/153b27eb5325f878ad4bb7ffe540c2ef
 *
 * Wraps an [onClick] lambda with another one that supports debouncing. The default deboucing time
 * is 1000ms.
 *
 * @return debounced onClick
 */

@Composable
inline fun debounced(
    debounceTime: Long = DEBOUNCE_DELAY,
    crossinline onClick: () -> Unit,
): () -> Unit {
    val lastTimeClicked = rememberSaveable { mutableLongStateOf(0L) }
    val onClickLambda: () -> Unit = {
        val now = SystemClock.uptimeMillis()
        if (now - lastTimeClicked.longValue > debounceTime) {
            onClick()
        }
        lastTimeClicked.longValue = now
    }
    return onClickLambda
}