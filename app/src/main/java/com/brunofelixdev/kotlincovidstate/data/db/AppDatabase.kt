package com.brunofelixdev.kotlincovidstate.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.brunofelixdev.kotlincovidstate.data.db.dao.WorldTotalDao
import com.brunofelixdev.kotlincovidstate.data.db.entity.WorldTotalEntity

@Database(
    entities = [WorldTotalEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun worldTotalDao() : WorldTotalDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabse(context).also {
                instance = it
            }
        }

        private fun buildDatabse(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "my_database.db"
            ).build()
    }

}