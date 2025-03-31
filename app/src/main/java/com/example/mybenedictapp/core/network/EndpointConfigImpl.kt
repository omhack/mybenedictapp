package com.example.mybenedictapp.core.network

import com.example.mybenedictapp.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EndpointConfigImpl @Inject constructor(
): EndpointConfig {
    override val baseApiUrl: String = BuildConfig.BASE_URL_API
}