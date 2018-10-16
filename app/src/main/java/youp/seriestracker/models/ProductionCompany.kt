package youp

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductionCompany : Parcelable {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("logo_path")
    @Expose
    var logoPath: Any? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("origin_country")
    @Expose
    var originCountry: String? = null

    protected constructor(`in`: Parcel) {
        this.id = `in`.readValue(Int::class.java.classLoader) as Int
        this.logoPath = `in`.readValue(Any::class.java.classLoader) as Any
        this.name = `in`.readValue(String::class.java.classLoader) as String
        this.originCountry = `in`.readValue(String::class.java.classLoader) as String
    }

    constructor() {}

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(id)
        dest.writeValue(logoPath)
        dest.writeValue(name)
        dest.writeValue(originCountry)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<ProductionCompany> = object : Creator<ProductionCompany> {


            override fun createFromParcel(`in`: Parcel): ProductionCompany {
                return ProductionCompany(`in`)
            }

            override fun newArray(size: Int): Array<ProductionCompany?> {
                return arrayOfNulls(size)
            }

        }
    }

}