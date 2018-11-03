package youp.seriestracker.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class SeriesResponse(
        var StatusCode: Int?,
        @SerializedName("Body")
        var series: List<Series>? ) : Parcelable {



}