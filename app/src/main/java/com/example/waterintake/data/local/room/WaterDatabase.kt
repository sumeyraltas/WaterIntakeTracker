package com.example.waterintake.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WaterIntakeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WaterDatabase : RoomDatabase() {
    abstract fun waterDao(): WaterDao

    companion object {
        const val DATABASE_NAME = "aquatrack_water.db"
    }
}
