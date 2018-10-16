import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import youp.Series

class SeriesResponse : Parcelable {

    @SerializedName("StatusCode")
    @Expose
    var statusCode: Int? = null
    @SerializedName("Body")
    @Expose
    var body: List<Series>? = null

    protected constructor(`in`: Parcel) {
        this.statusCode = `in`.readValue(Int::class.java.classLoader) as Int
        `in`.readList(this.body, youp.Series::class.java!!.getClassLoader())
    }

    constructor() {}

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(statusCode)
        dest.writeList(body)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<SeriesResponse> = object : Creator<SeriesResponse> {


            override fun createFromParcel(`in`: Parcel): SeriesResponse {
                return SeriesResponse(`in`)
            }

            override fun newArray(size: Int): Array<SeriesResponse?> {
                return arrayOfNulls(size)
            }

        }
    }

}