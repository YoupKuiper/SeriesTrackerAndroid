package youp

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Season : Parcelable {

    @SerializedName("air_date")
    @Expose
    var airDate: String? = null
    @SerializedName("episode_count")
    @Expose
    var episodeCount: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null
    @SerializedName("season_number")
    @Expose
    var seasonNumber: Int? = null

    protected constructor(`in`: Parcel) {
        this.airDate = `in`.readValue(String::class.java.classLoader) as String
        this.episodeCount = `in`.readValue(Int::class.java.classLoader) as Int
        this.id = `in`.readValue(Int::class.java.classLoader) as Int
        this.name = `in`.readValue(String::class.java.classLoader) as String
        this.overview = `in`.readValue(String::class.java.classLoader) as String
        this.posterPath = `in`.readValue(String::class.java.classLoader) as String
        this.seasonNumber = `in`.readValue(Int::class.java.classLoader) as Int
    }

    constructor() {}

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(airDate)
        dest.writeValue(episodeCount)
        dest.writeValue(id)
        dest.writeValue(name)
        dest.writeValue(overview)
        dest.writeValue(posterPath)
        dest.writeValue(seasonNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<Season> = object : Creator<Season> {


            override fun createFromParcel(`in`: Parcel): Season {
                return Season(`in`)
            }

            override fun newArray(size: Int): Array<Season?> {
                return arrayOfNulls(size)
            }

        }
    }

}