package santiago.academy.flickr

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import santiago.academy.flickr.databinding.ActivityMainBinding
import java.lang.Exception

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val flickrRecyclerViewAdapter = FlickrRecyclerViewAdapter(ArrayList())
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = activityMainBinding.root
        setContentView(rootView)
        setSupportActionBar(activityMainBinding.toolbar)
        var recyclerView = activityMainBinding.constraintLayout.recyclerviewMain
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = flickrRecyclerViewAdapter

        // format the URL to filter search and set language
        val url = createUri("https://www.flickr.com/services/feeds/photos_public.gne", "sunset", "en-us", true)
        // Calls Async function to retrieve the data from the URL
        val getRawData = GetRawData((object : GetRawData.OnDownloadCompleted {
            override fun onDownloadCompleted(data: String, status: DownloadStatus) {
                if (status == DownloadStatus.OK) {
                    Log.d(TAG, "onDownloadCompleted called, data is $data")

                    // parse the JSON data retrieve from getRawData
                    val getFlickrJsonData = GetFlickrJsonData(object : GetFlickrJsonData.OnDataAvailable {
                        override fun onDataAvailable(data: List<Photo>) {
                            Log.d(TAG, "onDataAvailable called")
                            flickrRecyclerViewAdapter.loadNewData(data)
                            Log.d(TAG, "onDataAvailable ends")
                        }

                        override fun onError(exception: Exception) {
                            Log.e(TAG, "onError called, exception $exception")
                        }
                    })
                    getFlickrJsonData.execute(data)
                } else {


                    Log.d(TAG, "onDownloadCompleted failed with status $status. Error message is: $data ")
                }
            }
        }))

        getRawData.execute(url)

        // assign the floating action button
        fab = activityMainBinding.fab
        // assign a onClickListener to our fab
        fab.setOnClickListener {
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG).show()
            Log.d(TAG, "CLICK CALLED")
        }
    }

    private fun createUri(baseUrl: String, searchCriteria: String, lang: String, matchAll: Boolean): String {
        Log.d(TAG, "createUri starts")

        return Uri.parse(baseUrl).buildUpon().
            appendQueryParameter("tags", searchCriteria).
            appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY").
            appendQueryParameter("lang", lang).
            appendQueryParameter("format", "json").
            appendQueryParameter("nojsoncallback", "1").
            build().toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.moreInformation -> {
                true
            }
            R.id.help -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}