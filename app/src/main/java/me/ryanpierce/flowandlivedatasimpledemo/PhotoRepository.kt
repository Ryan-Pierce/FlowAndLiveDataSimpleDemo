package me.ryanpierce.flowandlivedatasimpledemo

import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class PhotoRepository(names: Flow<String>, context: Context) {

    val flowOfPhotos: Flow<Photo>
            = names
                .map { it.getPhoto(context) }
                .onEach { delay(1000L) }
}