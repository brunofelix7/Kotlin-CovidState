package com.brunofelixdev.kotlincovidstate.model

data class WorldData(
        val confirmed: Long?,
        val recovered: Long?,
        val critical: Long?,
        val deaths: Long?,
        val lastChange: String?,
        val lastUpdate: String?
)