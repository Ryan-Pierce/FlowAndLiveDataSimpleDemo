package me.ryanpierce.flowandlivedatasimpledemo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

class UseCase(repository: PhotoRepository, names: Flow<String>) {

    val userFlow: Flow<User>
            = names
                .zip(repository.flowOfPhotos) { name, photo ->
                    User(name, photo)
                }
}