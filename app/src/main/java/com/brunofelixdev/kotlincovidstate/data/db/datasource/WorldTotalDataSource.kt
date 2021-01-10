package com.brunofelixdev.kotlincovidstate.data.db.datasource

import androidx.lifecycle.LiveData
import com.brunofelixdev.kotlincovidstate.data.db.dao.WorldTotalDao
import com.brunofelixdev.kotlincovidstate.data.db.entity.WorldTotalEntity

class WorldTotalDataSource(private val dao: WorldTotalDao) {

    suspend fun save(obj: WorldTotalEntity) : Long {
        return dao.insert(obj)
    }

    fun list() : LiveData<WorldTotalEntity> {
        return dao.find()
    }

}