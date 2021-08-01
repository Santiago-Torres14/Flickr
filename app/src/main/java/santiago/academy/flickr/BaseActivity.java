package santiago.academy.flickr;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {
    static final String FLICKR_QUERY = "FLICK_QUERY";
    static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";
    private final String TAG = "BaseActivity";

    public void activateToolbar(Activity activity, Boolean enableHome) {
        Log.d(TAG, "activateToolbar Called");
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enableHome);
    }

}
