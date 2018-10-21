package youp.seriestracker.models

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Season(
        @SerializedName("air_date")
        var airDate: String?,
        @SerializedName("episode_count")
        var episodeCount: Int?,
        var id: Int?,
        var name: String?,
        var overview: String?,
        @SerializedName("poster_path")
        var posterPath: String?,
        @SerializedName("season_number")
        var seasonNumber: Int?) : Parcelable {




}