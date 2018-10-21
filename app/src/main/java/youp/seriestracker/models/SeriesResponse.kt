package youp.seriestracker.models

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class SeriesResponse(
        var StatusCode: Int?,
        @SerializedName("Body")
        var series: List<Series>? ) : Parcelable {



}