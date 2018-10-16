package youp

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Series : Parcelable {

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null
    @SerializedName("created_by")
    @Expose
    var createdBy: List<Any>? = null
    @SerializedName("episode_run_time")
    @Expose
    var episodeRunTime: List<Int>? = null
    @SerializedName("first_air_date")
    @Expose
    var firstAirDate: String? = null
    @SerializedName("genres")
    @Expose
    var genres: List<Genre>? = null
    @SerializedName("homepage")
    @Expose
    var homepage: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("in_production")
    @Expose
    var inProduction: Boolean? = null
    @SerializedName("languages")
    @Expose
    var languages: List<String>? = null
    @SerializedName("last_air_date")
    @Expose
    var lastAirDate: String? = null
    @SerializedName("last_episode_to_air")
    @Expose
    var lastEpisodeToAir: LastEpisodeToAir? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("next_episode_to_air")
    @Expose
    var nextEpisodeToAir: NextEpisodeToAir? = null
    @SerializedName("networks")
    @Expose
    var networks: List<Network>? = null
    @SerializedName("number_of_episodes")
    @Expose
    var numberOfEpisodes: Int? = null
    @SerializedName("number_of_seasons")
    @Expose
    var numberOfSeasons: Int? = null
    @SerializedName("origin_country")
    @Expose
    var originCountry: List<Any>? = null
    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null
    @SerializedName("original_name")
    @Expose
    var originalName: String? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null
    @SerializedName("production_companies")
    @Expose
    var productionCompanies: List<ProductionCompany>? = null
    @SerializedName("seasons")
    @Expose
    var seasons: List<Season>? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Int? = null
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null

    protected constructor(`in`: Parcel) {
        this.backdropPath = `in`.readValue(String::class.java.classLoader) as String
        `in`.readList(this.createdBy, Any::class.java.classLoader)
        `in`.readList(this.episodeRunTime, Int::class.java.classLoader)
        this.firstAirDate = `in`.readValue(String::class.java.classLoader) as String
        `in`.readList(this.genres, youp.Genre::class.java.classLoader)
        this.homepage = `in`.readValue(String::class.java.classLoader) as String
        this.id = `in`.readValue(Int::class.java.classLoader) as Int
        this.inProduction = `in`.readValue(Boolean::class.java.classLoader) as Boolean
        `in`.readList(this.languages, java.lang.String::class.java.classLoader)
        this.lastAirDate = `in`.readValue(String::class.java.classLoader) as String
        this.lastEpisodeToAir = `in`.readValue(LastEpisodeToAir::class.java.classLoader) as LastEpisodeToAir
        this.name = `in`.readValue(String::class.java.classLoader) as String
        this.nextEpisodeToAir = `in`.readValue(NextEpisodeToAir::class.java.classLoader) as NextEpisodeToAir
        `in`.readList(this.networks, youp.Network::class.java.classLoader)
        this.numberOfEpisodes = `in`.readValue(Int::class.java.classLoader) as Int
        this.numberOfSeasons = `in`.readValue(Int::class.java.classLoader) as Int
        `in`.readList(this.originCountry, Any::class.java.classLoader)
        this.originalLanguage = `in`.readValue(String::class.java.classLoader) as String
        this.originalName = `in`.readValue(String::class.java.classLoader) as String
        this.overview = `in`.readValue(String::class.java.classLoader) as String
        this.popularity = `in`.readValue(Double::class.java.classLoader) as Double
        this.posterPath = `in`.readValue(String::class.java.classLoader) as String
        `in`.readList(this.productionCompanies, youp.ProductionCompany::class.java.classLoader)
        `in`.readList(this.seasons, youp.Season::class.java.classLoader)
        this.status = `in`.readValue(String::class.java.classLoader) as String
        this.type = `in`.readValue(String::class.java.classLoader) as String
        this.voteAverage = `in`.readValue(Int::class.java.classLoader) as Int
        this.voteCount = `in`.readValue(Int::class.java.classLoader) as Int
    }

    constructor() {}

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(backdropPath)
        dest.writeList(createdBy)
        dest.writeList(episodeRunTime)
        dest.writeValue(firstAirDate)
        dest.writeList(genres)
        dest.writeValue(homepage)
        dest.writeValue(id)
        dest.writeValue(inProduction)
        dest.writeList(languages)
        dest.writeValue(lastAirDate)
        dest.writeValue(lastEpisodeToAir)
        dest.writeValue(name)
        dest.writeValue(nextEpisodeToAir)
        dest.writeList(networks)
        dest.writeValue(numberOfEpisodes)
        dest.writeValue(numberOfSeasons)
        dest.writeList(originCountry)
        dest.writeValue(originalLanguage)
        dest.writeValue(originalName)
        dest.writeValue(overview)
        dest.writeValue(popularity)
        dest.writeValue(posterPath)
        dest.writeList(productionCompanies)
        dest.writeList(seasons)
        dest.writeValue(status)
        dest.writeValue(type)
        dest.writeValue(voteAverage)
        dest.writeValue(voteCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<SeriesResponse> = object : Creator<SeriesResponse> {


            override fun createFromParcel(`in`: Parcel): SeriesResponse {
                return SeriesResponse(`in`)
            }

            override fun newArray(size: Int): Array<SeriesResponse?> {
                return arrayOfNulls(size)
            }

        }
    }

}
