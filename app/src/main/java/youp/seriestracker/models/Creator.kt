package youp.seriestracker.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Creator(
        @SerializedName("id") val id: Int?,
        @SerializedName("credit_id") val creditId: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("gender") val gender: Int?,
        @SerializedName("profile_path") val profilePath: String?) : Parcelable {
}