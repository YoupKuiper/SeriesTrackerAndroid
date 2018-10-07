package youp.seriestracker.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User {

    @SerializedName("emailAddress")
    @Expose
    var emailAddress: String? = null
    @SerializedName("password")
    @Expose
    var password: String? = null
    @SerializedName("authToken")
    @Expose
    var authToken: String? = null

}