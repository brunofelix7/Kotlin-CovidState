package com.brunofelixdev.kotlincovidstate.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_ID = 0

@Entity(tableName = "world_total")
data class WorldTotalEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = CURRENT_ID,
    val confirmed: Long?,
    val recovered: Long?,
    val critical: Long?,
    val deaths: Long?,
    val lastChange: String?,
    val lastUpdate: String?
)
