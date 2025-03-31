package com.example.mybenedictapp.core.di

import com.example.mybenedictapp.core.network.EndpointConfig
import com.example.mybenedictapp.core.network.EndpointConfigImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModuleBinds {
    @Binds
    @Singleton
    fun bindEndpointConfig(impl: EndpointConfigImpl): EndpointConfig
}