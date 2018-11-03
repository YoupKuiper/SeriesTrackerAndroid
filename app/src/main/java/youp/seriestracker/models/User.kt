package youp.seriestracker.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val emailAddress: String?, val password: String?, val authToken: String?) : Parcelable {

}