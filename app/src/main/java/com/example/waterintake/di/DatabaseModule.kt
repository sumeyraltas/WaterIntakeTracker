package com.example.waterintake.di

import android.content.Context
import androidx.room.Room
import com.example.waterintake.data.local.room.WaterDao
import com.example.waterintake.data.local.room.WaterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWaterDatabase(
        @ApplicationContext context: Context
    ): WaterDatabase {
        return Room.databaseBuilder(
            context,
            WaterDatabase::class.java,
            WaterDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideWaterDao(database: WaterDatabase): WaterDao {
        return database.waterDao()
    }
}
