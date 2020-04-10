package me.ryanpierce.flowandlivedatasimpledemo

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

/**
 * @property name the name of the Photo (Also the user in the Photo).
 */
data class Photo(var name: String, var image: Drawable?)

// This demo project uses drawables from the resource folder to create Photos.
suspend fun String.getPhoto(context: Context): Photo = withContext(Dispatchers.IO) {
    suspendCancellableCoroutine<Photo> { cancellableContinuation ->
        val drawable = with(context) {
            val resourceId = resources.getIdentifier(
                this@getPhoto.toLowerCase(),
                "drawable",
                packageName
            )
            ContextCompat.getDrawable(this, resourceId)
        }
        val photo = Photo(this@getPhoto, drawable)
        cancellableContinuation.resume(photo)
    }
}