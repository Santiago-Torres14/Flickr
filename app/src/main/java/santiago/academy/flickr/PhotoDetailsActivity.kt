package santiago.academy.flickr

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.squareup.picasso.Picasso
import santiago.academy.flickr.databinding.ActivityPhotoDetailsBinding

class PhotoDetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityPhotoDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhotoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activateToolbar(this, true)

        val photo = intent.getParcelableExtra<Photo>(PHOTO_TRANSFER)

        binding.ConstraintLayout.photoTitle.text = photo?.title
        binding.ConstraintLayout.photoTags.text = photo?.tags
        binding.ConstraintLayout.photoAuthor.text = photo?.author
        Picasso
            .get()
            .load(photo?.link)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(binding.ConstraintLayout.photoImage)
    }

}