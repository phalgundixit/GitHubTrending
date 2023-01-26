package com.phalgundixit.githubtrending.base.di


import com.phalgundixit.githubtrending.repository.GithubRepository
import com.phalgundixit.githubtrending.repository.GithubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesGithubRepository(impl: GithubRepositoryImpl): GithubRepository
}