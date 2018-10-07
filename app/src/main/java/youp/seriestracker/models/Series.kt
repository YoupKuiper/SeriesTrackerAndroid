package youp.seriestracker.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Series {

    @SerializedName("statusCode")
    @Expose
    var statusCode: Int? = null
    @SerializedName("body")
    @Expose
    var body: Body? = null

}