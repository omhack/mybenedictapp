package com.example.mybenedictapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mybenedictapp.ui.screens.MovieListScreen
import com.example.mybenedictapp.ui.theme.MyBenedictAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyBenedictAppTheme {
                MovieListScreen()
            }
        }
    }
}
