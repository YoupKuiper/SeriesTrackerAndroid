package youp

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Network : Parcelable {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("logo_path")
    @Expose
    var logoPath: String? = null
    @SerializedName("origin_country")
    @Expose
    var originCountry: String? = null

    protected constructor(`in`: Parcel) {
        this.name = `in`.readValue(String::class.java.classLoader) as String
        this.id = `in`.readValue(Int::class.java.classLoader) as Int
        this.logoPath = `in`.readValue(String::class.java.classLoader) as String
        this.originCountry = `in`.readValue(String::class.java.classLoader) as String
    }

    constructor() {}

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(name)
        dest.writeValue(id)
        dest.writeValue(logoPath)
        dest.writeValue(originCountry)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<Network> = object : Creator<Network> {


            override fun createFromParcel(`in`: Parcel): Network {
                return Network(`in`)
            }

            override fun newArray(size: Int): Array<Network?> {
                return arrayOfNulls(size)
            }

        }
    }

}