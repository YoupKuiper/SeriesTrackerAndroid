package youp

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NextEpisodeToAir : Parcelable {

    @SerializedName("air_date")
    @Expose
    var airDate: String? = null
    @SerializedName("episode_number")
    @Expose
    var episodeNumber: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("production_code")
    @Expose
    var productionCode: Any? = null
    @SerializedName("season_number")
    @Expose
    var seasonNumber: Int? = null
    @SerializedName("show_id")
    @Expose
    var showId: Int? = null
    @SerializedName("still_path")
    @Expose
    var stillPath: Any? = null
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Int? = null
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null

    protected constructor(`in`: Parcel) {
        this.airDate = `in`.readValue(String::class.java.classLoader) as String
        this.episodeNumber = `in`.readValue(Int::class.java.classLoader) as Int
        this.id = `in`.readValue(Int::class.java.classLoader) as Int
        this.name = `in`.readValue(String::class.java.classLoader) as String
        this.overview = `in`.readValue(String::class.java.classLoader) as String
        this.productionCode = `in`.readValue(Any::class.java.classLoader) as Any
        this.seasonNumber = `in`.readValue(Int::class.java.classLoader) as Int
        this.showId = `in`.readValue(Int::class.java.classLoader) as Int
        this.stillPath = `in`.readValue(Any::class.java.classLoader) as Any
        this.voteAverage = `in`.readValue(Int::class.java.classLoader) as Int
        this.voteCount = `in`.readValue(Int::class.java.classLoader) as Int
    }

    constructor() {}

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(airDate)
        dest.writeValue(episodeNumber)
        dest.writeValue(id)
        dest.writeValue(name)
        dest.writeValue(overview)
        dest.writeValue(productionCode)
        dest.writeValue(seasonNumber)
        dest.writeValue(showId)
        dest.writeValue(stillPath)
        dest.writeValue(voteAverage)
        dest.writeValue(voteCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<NextEpisodeToAir> = object : Creator<NextEpisodeToAir> {


            override fun createFromParcel(`in`: Parcel): NextEpisodeToAir {
                return NextEpisodeToAir(`in`)
            }

            override fun newArray(size: Int): Array<NextEpisodeToAir?> {
                return arrayOfNulls(size)
            }

        }
    }

}