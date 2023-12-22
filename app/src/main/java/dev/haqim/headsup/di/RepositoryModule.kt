package dev.haqim.headsup.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.haqim.headsup.data.repository.NewsRepository
import dev.haqim.headsup.domain.repository.INewsRepository

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun provideNewsRepository(repository: NewsRepository): INewsRepository
}