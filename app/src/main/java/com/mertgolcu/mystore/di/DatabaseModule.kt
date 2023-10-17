package com.mertgolcu.mystore.di

import com.mertgolcu.mystore.data.local.database.CozyDatabase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author mertgolcu
 * @since 16.10.2023
 */
@Module()
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // provide room database
}