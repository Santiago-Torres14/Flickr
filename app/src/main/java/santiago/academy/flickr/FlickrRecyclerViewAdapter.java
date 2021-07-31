package santiago.academy.flickr;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder>{
    static private final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> photoList;

    public FlickrRecyclerViewAdapter(@NonNull List<Photo> photoList){
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public FlickrImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Called by the layout manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder called new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);
        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrImageViewHolder holder, int position) {
        // called by the layout manager when it wants new data in an existing view
        Photo photoItem = photoList.get(position);
        Log.d(TAG, "onBindViewHolder: "+photoItem+" --> "+position);
        Picasso.get().load(photoItem.getImage()).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(holder.thumbnail);
        holder.title.setText(photoItem.getTitle());
    }

    public void loadNewData(List<Photo> newPhotos){
        photoList = newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position){
        return photoList.isEmpty() ? null : photoList.get(position);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount called");
        return photoList.isEmpty() ? 0 : photoList.size();
    }

    static public class FlickrImageViewHolder extends RecyclerView.ViewHolder {
        protected ImageView thumbnail;
        protected TextView title;

        public FlickrImageViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.element_title);
        }
    }
}
