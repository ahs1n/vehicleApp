package com.example.vehicleapp.di.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vehicleapp.model.Attendance
import com.example.vehicleapp.model.UsersItem
import com.example.vehicleapp.model.VehiclesItem
import com.example.vehicleapp.utils.CONSTANTS.DATABASE_NAME
import com.example.vehicleapp.utils.CONSTANTS.DATABASE_VERSION

@Database(
    entities = [UsersItem::class, VehiclesItem::class, Attendance::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class VehicleAppDatabase : RoomDatabase() {

    abstract fun vehicleDao(): VehicleDao

    companion object {
        @Volatile
        private var INSTANCE: VehicleAppDatabase? = null

        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                VehicleAppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

    }
}