package com.uci.shopapp.di

import android.app.Application
import androidx.room.Room
import com.uci.shopapp.data.model.database.ShopDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(appContext:Application):ShopDatabase{
        return Room.databaseBuilder(appContext, ShopDatabase::class.java,"database").build()
    }
}