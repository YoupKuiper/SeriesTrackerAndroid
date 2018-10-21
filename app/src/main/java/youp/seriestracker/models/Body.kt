package youp.seriestracker.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Body() : Parcelable {
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("backdrop_path")
    @Expose
    var imageURL: String? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        imageURL = parcel.readString()
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel?, p1: Int) {
        if (out != null) {
            out.writeString(name)
            out.writeString(imageURL)
        }
    }

    companion object CREATOR : Parcelable.Creator<Body> {
        override fun createFromParcel(parcel: Parcel): Body {
            return Body(parcel)
        }

        override fun newArray(size: Int): Array<Body?> {
            return arrayOfNulls(size)
        }
    }
}