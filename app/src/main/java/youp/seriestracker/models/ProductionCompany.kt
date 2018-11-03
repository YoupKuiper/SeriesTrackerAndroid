package youp.seriestracker.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProductionCompany(
        var id: Int?,
        @SerializedName("logo_path")
        var logoPath: String?,
        var name: String?,
        @SerializedName("origin_country")
        var originCountry: String? ) : Parcelable {




}