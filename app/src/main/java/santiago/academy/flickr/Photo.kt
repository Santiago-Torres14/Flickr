package santiago.academy.flickr

import android.os.Parcelable
import android.util.Log
import java.io.IOException
import java.io.ObjectStreamException
import kotlinx.parcelize.Parcelize

@Parcelize
class Photo(var title: String,
            var author: String,
            var authorId: String,
            var link: String,
            var tags: String,
            var image: String) : Parcelable {

    override fun toString(): String {
        return "Photo(title='$title', author='$author', authorId='$authorId', link='$link', tag='$tags', image='$image')"
    }

}