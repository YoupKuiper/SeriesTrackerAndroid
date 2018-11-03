package youp.seriestracker.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class SimpleSeriesResponse(
        @SerializedName("page")
        var page: Int?,
        @SerializedName("total_results")
        var totalResults: Int?,
        @SerializedName("total_pages")
        var totalPages: Int?,
        @SerializedName("results")
        var series: List<Series>? ) : Parcelable {

}