package me.ryanpierce.flowandlivedatasimpledemo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

class UseCase(repository: PhotoRepository, names: Flow<String>) {

    // Requires the name flow to emit in the same order as the photo flow.
    val userFlow: Flow<User>
            = names
                .zip(repository.flowOfPhotos) { name, photo ->
                    User(name, photo)
                }
}