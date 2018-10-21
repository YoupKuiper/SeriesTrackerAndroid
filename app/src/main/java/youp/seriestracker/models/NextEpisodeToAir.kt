package youp.seriestracker.models

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class NextEpisodeToAir(
        @SerializedName("air_date")
        var airDate: String?,
        @SerializedName("episode_number")
        var episodeNumber: Int?,
        var id: Int?,
        var name: String?,
        var overview: String?,
        @SerializedName("season_number")
        var seasonNumber: Int?,
        @SerializedName("show_id")
        var showId: Int?,
        @SerializedName("vote_average")
        var voteAverage: Double?,
        @SerializedName("vote_count")
        var voteCount: Int?
) : Parcelable {



}