package santiago.academy.flickr

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class RecycleItemClickListener(context: Context, recycleView: RecyclerView, private val listener: OnRecyclerClickListener)
    : RecyclerView.SimpleOnItemTouchListener() {

    private val TAG = "RecyclerItemClickListen"

    interface OnRecyclerClickListener {
        fun onItemClicked(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    // ass the gesture detector
    private val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            val childView = e?.let { recycleView.findChildViewUnder(it.x, e.y) }
            Log.d(TAG, "onSingleTapUp calling listener onItemClick")
            if (childView != null){
                listener.onItemClicked(childView, recycleView.getChildAdapterPosition(childView))
            }
            return super.onSingleTapUp(e)
        }

        override fun onLongPress(e: MotionEvent?) {
            val childView = e?.let { recycleView.findChildViewUnder(it.x, e.y) }
            Log.d(TAG, "onLongPress calling listener onItemLongClick")
            if (childView != null){
                listener.onItemLongClick(childView, recycleView.getChildAdapterPosition(childView))
            }
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(e)
    }
}