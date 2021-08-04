package santiago.academy.flickr

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import santiago.academy.flickr.databinding.ActivityMainBinding
import java.lang.Exception
import androidx.preference.PreferenceManager


private const val TAG = "MainActivity"

class MainActivity : BaseActivity(), RecycleItemClickListener.OnRecyclerClickListener,
    GetRawData.OnDownloadCompleted, GetFlickrJsonData.OnDataAvailable {

    private val flickrRecyclerViewAdapter = FlickrRecyclerViewAdapter(ArrayList())
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = activityMainBinding.root
        setContentView(rootView)

        activateToolbar(this, false)

        val recyclerView = activityMainBinding.ConstraintLayout.recyclerviewMain
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addOnItemTouchListener(RecycleItemClickListener(this, recyclerView, this))
        recyclerView.adapter = flickrRecyclerViewAdapter

        // assign the floating action button
        fab = activityMainBinding.fab
        // assign a onClickListener to our fab
        fab.setOnClickListener {
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG).show()
            Log.d(TAG, "CLICK CALLED")
        }
    }

    override fun onItemClicked(view: View, position: Int) {
        Log.d(TAG, "onItemClicked called")
        Toast.makeText(this, "Normal tap at position $position", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, "onItemLongClick called")
//        Toast.makeText(this, "Long tap at position $position", Toast.LENGTH_SHORT).show()
        val photo = flickrRecyclerViewAdapter.getPhoto(position)
        if (photo != null) {
            val intent = Intent(this, PhotoDetailsActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER, photo)
            startActivity(intent)
        }
    }

    override fun onDownloadCompleted(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onDownloadCompleted called, data is $data")

            // parse the JSON data retrieve from getRawData
            val getFlickrJsonData = GetFlickrJsonData(this)
            getFlickrJsonData.execute(data)
        } else {
            Log.d(TAG, "onDownloadCompleted failed with status $status. Error message is: $data ")
        }
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG, "onDataAvailable called")
        flickrRecyclerViewAdapter.loadNewData(data)
        Log.d(TAG, "onDataAvailable ends")
    }

    override fun onError(exception: Exception) {
        Log.e(TAG, "onError called, exception $exception")
    }

    private fun createUri(
        baseUrl: String,
        searchCriteria: String,
        lang: String,
        matchAll: Boolean
    ): String {
        Log.d(TAG, "createUri starts")

        return Uri.parse(baseUrl).buildUpon().appendQueryParameter("tags", searchCriteria)
            .appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY")
            .appendQueryParameter("lang", lang).appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1").build().toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val queryResult = sharedPref.getString(FLICKR_QUERY, "")
        if (queryResult?.isNotEmpty() == true){
            // format the URL to filter search and set language
            val url = createUri(
                "https://www.flickr.com/services/feeds/photos_public.gne",
                queryResult,
                "en-us",
                true
            )
            // Calls Async function to retrieve the data from the URL
            val getRawData = GetRawData(this)

            getRawData.execute(url)
        }
    }
}