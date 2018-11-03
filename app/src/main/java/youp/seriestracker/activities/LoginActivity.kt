package youp.seriestracker.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import youp.seriestracker.Models.User
import youp.seriestracker.R
import youp.seriestracker.webservices.APIService
import youp.seriestracker.webservices.RetrofitClient

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun login(){
        val retrofit = RetrofitClient.client
        val service = retrofit.create(APIService::class.java)

        val user = User("asd", "asd", "asd")
        val call = service.login(user)

        call.enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>?, response: Response<User>?) {

                if (response != null && response.isSuccessful) {
                    Toast.makeText(applicationContext, "asd", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "No Series Found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })


    }
}
