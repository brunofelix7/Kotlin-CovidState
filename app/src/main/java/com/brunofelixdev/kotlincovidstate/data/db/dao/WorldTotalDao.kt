package com.brunofelixdev.kotlincovidstate.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brunofelixdev.kotlincovidstate.data.db.entity.CURRENT_ID
import com.brunofelixdev.kotlincovidstate.data.db.entity.WorldTotalEntity

@Dao
interface WorldTotalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: WorldTotalEntity) : Long

    @Query("SELECT * FROM world_total WHERE id = $CURRENT_ID")
    fun find() : LiveData<WorldTotalEntity>

}