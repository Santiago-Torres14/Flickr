package santiago.academy.flickr;

import android.os.Bundle;
import android.util.Log;

import santiago.academy.flickr.databinding.ActivitySearchBinding;

public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    private ActivitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate starts");
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activateToolbar$Flickr_app(true);

        Log.d(TAG, "onCreate ends");
    }

}