package com.example.cafeteria.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cafeteria.R
import com.example.cafeteria.USER_DATA
import com.example.cafeteria.activities.SendOTP
import com.example.cafeteria.helpers.UserHelper
import com.example.cafeteria.models.RegisterRequest
import com.example.cafeteria.models.RegisterResponse
import com.example.cafeteria.services.ApiClient
import com.example.cafeteria.services.RegisterService
import com.example.cafeteria.services.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var etName: EditText? = null
var etEmail: EditText? = null
var etPass: EditText? = null
var etConfirmPass: EditText? = null
var etMobileNum: EditText? = null
var signUpBtn: Button? = null



//To get access token:
private lateinit var sessionManager: SessionManager

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        etName = findViewById(R.id.name)
        etEmail = findViewById(R.id.email)
        etPass = findViewById(R.id.pass)
        etConfirmPass = findViewById(R.id.confirm_pass)
        etMobileNum = findViewById(R.id.mobile_num)
        signUpBtn = findViewById(R.id.signup_btn)

        sessionManager = SessionManager(this@SignupActivity)

        registerUI()

    }

    private fun registerUI() {
        // Sign Up Button
        signUpBtn!!.setOnClickListener {
            val name = etName!!.text.toString()
            val email = etEmail!!.text.toString()
            val password = etPass!!.text.toString()
            val confirmPassowrd = etConfirmPass!!.text.toString()
            val mobileNumber = etMobileNum!!.text.toString()

            signUpBtn!!.isActivated = false
            //first validate the fields:
            if (UserHelper.validateRegisterData(
                    this@SignupActivity,
                    etName,
                    etEmail,
                    etPass,
                    etConfirmPass,
                    etMobileNum
                )
            ) {

                //second check if data is correct:
                val registerRequest = RegisterRequest(name, email, password, mobileNumber)
                val registerService: RegisterService = ApiClient(this@SignupActivity).buildService(
                    RegisterService::class.java
                )
                val requestCall: Call<RegisterResponse> =
                    registerService.userRegister(registerRequest)
                requestCall.enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        if (response.isSuccessful) {
                           // sessionManager.saveAccessTokenRegister(response.body()!!)
                               Toast.makeText(this@SignupActivity, "Logined successfully", Toast.LENGTH_LONG).show()

                            goToHome(response.body()!!)
                        } else {
                            val errorCode: String = when (response.code()) {
                                400 -> {
                                    "Incorrect email or password."
                                }
                                404 -> {
                                    "404 not found"
                                }
                                500 -> {
                                    "500 server broken"
                                }
                                else -> {
                                    "Unknown error!"
                                }
                            }

                            signUpBtn!!.isActivated = true

                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

                        signUpBtn!!.isActivated = true


                    }

                })


            }
        }
    }



    /**
     * To go to verify by mobile number  with register response data:
     * */
    private fun goToHome(registerResponse: RegisterResponse){
        val intent = Intent(this@SignupActivity, SendOTP::class.java)
        intent.putExtra(USER_DATA,registerResponse)
        startActivity(intent)
        finish()
    }
}