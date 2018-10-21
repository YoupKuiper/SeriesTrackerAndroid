package youp.seriestracker.models

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Network(
        val name: String?,
        val id: Int?,
        @SerializedName("logo_path")
        val logoPath: String?,
        @SerializedName("origin_country")
        val originCountry: String?) : Parcelable {

}