package youp.seriestracker.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class LastEpisodeToAir(
        @SerializedName("air_date")
        val airDate: String?,
        @SerializedName("episode_number")
        val episodeNumber: Int?,
        val id: Int?,
        val name: String?,
        val overview: String?,
        @SerializedName("season_number")
        val seasonNumber: Int?,
        @SerializedName("show_id")
        val showId: Int?,
        val stillpath: String?,
        @SerializedName("vote_average")
        val voteAverage: Double?,
        @SerializedName("vote_count")
        val voteCount: Int?) : Parcelable {
}