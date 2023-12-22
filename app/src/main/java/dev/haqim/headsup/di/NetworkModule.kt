package dev.haqim.headsup.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.haqim.headsup.data.remote.service.NewsListService
import dev.haqim.headsup.data.remote.util.ApiConfig
import dev.haqim.headsup.data.remote.util.TokenInterceptor
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(tokenInterceptor)

    @Provides
    fun provideApiConfig(
        okHttpClient: OkHttpClient.Builder
    ): ApiConfig = ApiConfig.getInstance(okHttpClient)

    @Provides
    fun provideNewsListService(
        apiConfig: ApiConfig
    ): NewsListService = apiConfig.createService(NewsListService::class.java)
}