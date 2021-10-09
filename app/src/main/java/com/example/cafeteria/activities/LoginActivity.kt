package com.example.cafeteria.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeteria.*
import com.example.cafeteria.helpers.UserHelper
import com.example.cafeteria.models.UserRequest
import  com.example.cafeteria.models.UserResponse
import  com.example.cafeteria.services.ApiClient
import  com.example.cafeteria.services.LoginService

import com.example.cafeteria.services.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    var etemail: EditText? = null
    var etpassword: EditText? = null
    var loginBtn: Button? = null
    var forgetPassBtn : TextView? = null
    var signup:TextView?= null

    var skip :TextView? = null



    //To get access token:
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        signup = findViewById(R.id.go_to_signup)
        forgetPassBtn = findViewById(R.id.forget_pass)
        loginBtn = findViewById(R.id.loginBtn)
        etemail = findViewById(R.id.et_email)
        etpassword = findViewById(R.id.pass)
        skip = findViewById(R.id.skip)





        //!Reset password button
        forgetPassBtn!!.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SendOTP::class.java))

        }
        //go to Sign up Activity
        signup!!.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))

        }

        //go to main activity
        skip!!.setOnClickListener {
            startActivity(Intent(this@LoginActivity, GuestViewActivity::class.java))
        }

        sessionManager = SessionManager(this@LoginActivity)

        loginUI()


    }


    /**
     * Set up views UI:
     * */
    private fun loginUI(){

        //!Login button
        loginBtn!!.setOnClickListener {

            loginBtn!!.isActivated = false
            //first validate the fields:
            if(UserHelper.validateLoginData(this@LoginActivity, etemail, etpassword)){

                //second check if email or password are correct:
                val userRequest = UserRequest(etemail!!.text.toString(), etpassword!!.text.toString())
                val loginService:LoginService = ApiClient(this@LoginActivity).buildService(LoginService::class.java)
                val requestCall: Call<UserResponse> = loginService.userLogin(userRequest)
                requestCall.enqueue(object:Callback<UserResponse>{
                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                        if(response.isSuccessful){
                            sessionManager.saveAccessToken(response.body()!!)
                            Toast.makeText(this@LoginActivity,"Logined Successfully",Toast.LENGTH_LONG).show()
                            goToHome(response.body()!!)
                        }else{
                            val errorCode:String = when(response.code()){
                                400 ->{
                                    "Incorrect email or password."
                                }
                                404 -> {
                                    "404 not found"
                                }
                                500 -> {
                                    "500 server broken"
                                }
                                else ->{
                                    "Unknown error!"
                                }
                            }
                            loginBtn!!.isActivated = true
                            Toast.makeText(this@LoginActivity, "email or password is incorrect",Toast.LENGTH_LONG).show()

                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {

                        loginBtn!!.isActivated = true
                        Toast.makeText(this@LoginActivity, "email or password is incorrect",Toast.LENGTH_LONG).show()

                    }

                }
                )


            }

        }




    }

    /**
     * To go to home page with user response data:
     * */
    private fun goToHome(userResponse: UserResponse){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.putExtra(USER_DATA,userResponse)
        startActivity(intent)
        finish()
    }




}


