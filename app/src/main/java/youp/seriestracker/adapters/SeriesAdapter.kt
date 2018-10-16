package youp.seriestracker.adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import youp.seriestracker.R
import youp.seriestracker.models.Body
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.squareup.picasso.Picasso
import java.net.URL


internal class SeriesAdapter(var context: Context, var data: List<Body>?) : BaseAdapter() {

    val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

    init {
        inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?

    }// TODO Auto-generated constructor stub

    override fun getCount(): Int {
        // TODO Auto-generated method stub
        return data!!.size
    }

    override fun getItem(position: Int): Any {
        // TODO Auto-generated method stub
        return data!![position]
    }

    override fun getItemId(position: Int): Long {
        // TODO Auto-generated method stub
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // TODO Auto-generated method stub
        var vi: View? = convertView
        if (vi == null)
            vi = inflater!!.inflate(R.layout.list_item, null)
        val title = vi!!.findViewById(R.id.listitem_title) as TextView
        val image = vi.findViewById(R.id.listitem_image) as ImageView
        title.text = data!![position].name
        Picasso.get().load(BASE_IMG_URL + data!![position].imageURL).into(image)

        return vi
    }

    companion object {
        private var inflater: LayoutInflater? = null
    }
}