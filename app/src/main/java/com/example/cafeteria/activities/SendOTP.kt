package com.example.cafeteria.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.evapharma.cafeteriaapp.shortToast
import com.example.cafeteria.*
import com.example.cafeteria.models.PhoneResponse
import com.example.cafeteria.models.SendPhoneRequest
import com.example.cafeteria.services.ApiClient
import com.example.cafeteria.services.ResetPasswordService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendOTP : AppCompatActivity() {

    var sendOtpBtn: Button? = null
    var sendOtpNumInput: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_otp)
        sendOtpBtn = findViewById(R.id.send_otpBtn)
        sendOtpNumInput = findViewById(R.id.etSendotpInputmobile)
        initButtons()
        initEditTexts()

    }

    private fun isFormatPhone(phone: String): Boolean {
        return phone.startsWith(STARTER)
    }

    private fun isCorrectLength(phone: String, target_length: Int): Boolean {
        return phone.length == target_length
    }

    private fun callAPI(phone: String) {
        //call api here:
        sendOtpBtn!!.isActivated = false
        val resetPasswordService: ResetPasswordService =
            ApiClient(this@SendOTP).buildService(ResetPasswordService::class.java)
        val requestCall: Call<PhoneResponse> = resetPasswordService.sendPhoneNumber(
            SendPhoneRequest(phone)
        )
        requestCall.enqueue(object : Callback<PhoneResponse> {
            override fun onResponse(call: Call<PhoneResponse>, response: Response<PhoneResponse>) {
                if (response.isSuccessful) {
                    //TODO: go to verify OTP and send with it phone (intent)
                    sendOtpBtn!!.isActivated = true
                    Toast.makeText(this@SendOTP, "Succeded", Toast.LENGTH_LONG).show()
/*
                    val intent = Intent(this@SendOTP, LoginActivity::class.java)
                    intent.putExtra(PHONE_RESPONSE, sendOtpNumInput!!.text)
                    startActivity(intent)
                    finish()

 */
                    //To go to OTP from forget password

                    val intent = Intent(this@SendOTP, VerifyOTPActivity::class.java)
                    intent.putExtra(PHONE_RESPONSE, sendOtpNumInput!!.text)
                    startActivity(intent)
                    finish()




                }else{
                    val errorCode:String = when(response.code()){
                        400 -> {
                            "Phone number is not existed."
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

                    sendOtpBtn!!.isActivated=true
                }
            }

            override fun onFailure(call: Call<PhoneResponse>, t: Throwable) {
                sendOtpBtn!!.isActivated=true
            }

        })
    }

    private fun getOtp(phone:String):Boolean{
        if ( !isFormatPhone(phone)){
            shortToast(this,"Wrong phone format!")
            return false
        }
        if ( !isCorrectLength(phone, PHONE_LENGTH)){
            shortToast(this,"Wrong Phone number length!")
            return false
        }
        return true
    }
    private fun initButtons(){
        sendOtpBtn!!.setOnClickListener {
            if(sendOtpNumInput!!.text.isNotEmpty() && sendOtpNumInput!!.text.length== PHONE_LENGTH){
                callAPI(sendOtpNumInput!!.text.toString())

            }else{
                sendOtpNumInput!!.error = "wrong number!"
            }
        }
    }
    private fun initEditTexts(){
        sendOtpNumInput!!.doOnTextChanged { text, start, before, count ->
            //PHONE_NUMBER = text.toString()
            //There is Error Here
        }
    }

}