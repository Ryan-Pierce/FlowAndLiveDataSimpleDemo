package me.ryanpierce.flowandlivedatasimpledemo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class DataSource {

    companion object {
        val names: Flow<String>
                = listOf("Sarah", "Nathan", "Emily", "Alex")
                    .asFlow()
    }
}