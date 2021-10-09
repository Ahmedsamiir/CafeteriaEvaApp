package com.example.cafeteria.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeteria.PHONE_NUMBER
import com.example.cafeteria.PHONE_RESPONSE
import com.example.cafeteria.R
import com.example.cafeteria.USER_DATA
import com.example.cafeteria.models.SendOtpRequest
import com.example.cafeteria.models.SendOtpResponse
import com.example.cafeteria.services.ApiClient
import com.example.cafeteria.services.ResetPasswordService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyOTPActivity : AppCompatActivity() {
    private var listOfet: List<EditText> = listOf()
    private var sizeofets:Int=0
    var otpTimer:TextView? = null
    var verifyOtpBtn:Button? = null
    var resendOtp:TextView? = null
    var verifyOtpMobileNumber:TextView?=null
    var otpCode1:EditText?=null
    var otpCode2:EditText?=null
    var otpCode3:EditText?=null
    var otpCode4:EditText?=null
    var otpCode5:EditText?=null
    var otpCode6:EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)
        otpTimer = findViewById(R.id.tv_verifyotp_timer)
        verifyOtpBtn = findViewById(R.id.verify_otpBtn)
        resendOtp = findViewById(R.id.tv_resendotp_verifyotp)
        verifyOtpMobileNumber = findViewById(R.id.tv_verifyotp_mobilenumber)
        otpCode1 = findViewById(R.id.et_verifyotp_code1)
        otpCode2 = findViewById(R.id.et_verifyotp_code2)
        otpCode3 = findViewById(R.id.et_verifyotp_code3)
        otpCode4 = findViewById(R.id.et_verifyotp_code4)
        otpCode5 = findViewById(R.id.et_verifyotp_code5)
        otpCode6 = findViewById(R.id.et_verifyotp_code6)

        initButtons()
        initEditTexts()
        setViewPhonenumber()
        setUpTimer()


    }

    //Set up timer on page start:
    private fun setUpTimer(){
        object : CountDownTimer(300000,1000){
            override fun onTick(p0: Long) {
                val minute = (p0 / 1000) / 60
                val seconds = (p0 / 1000) % 60
                otpTimer!!.text = "$minute : $seconds"
            }

            override fun onFinish() {
               otpTimer!!.error= "OTP expired, please request new one."
            }
        }.start()
    }

    //to phone response from last page use if needed:
    private fun getPhoneResponse():String?{
        val bundle:Bundle? = intent.extras
        if(bundle?.containsKey(PHONE_RESPONSE)!!) {
            return intent.extras?.get(USER_DATA) as String
        }
        return null
    }

    private fun apiGetOTP(){
        if(isEtValid()){
            //call api and get the otp code of the user
            callApi()
        }
    }

    //check if all ETs are valid:
    private fun isEtValid():Boolean{
        var result:String=""
        for (idx in 0 until sizeofets-1){
            result+=listOfet[idx].text
        }
        return result.trim().length==sizeofets
    }

    //call api:
    private fun callApi(){

        verifyOtpBtn!!.isActivated=false
        val resetPasswordService: ResetPasswordService = ApiClient(this@VerifyOTPActivity).buildService(ResetPasswordService::class.java)
        val requestCall: Call<SendOtpResponse> = resetPasswordService.sendOtp(SendOtpRequest(getInput(),resendOtp!!.text.toString()))
        requestCall.enqueue(object: Callback<SendOtpResponse> {
            override fun onResponse(call: Call<SendOtpResponse>, response: Response<SendOtpResponse>) {
                if(response.isSuccessful){

                    verifyOtpBtn!!.isActivated=true
                    if(response.body()!!.isCorrect){
                        val intent = Intent(this@VerifyOTPActivity, MainActivity::class.java)
                        intent.putExtra(PHONE_RESPONSE,getPhoneResponse())
                        startActivity(Intent(this@VerifyOTPActivity, MainActivity::class.java))

                        finish()
                    }else{
                       error("Wrong OTP, try again.")
                    }
                }else{
                    val errorCode:String = when(response.code()){
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

                    verifyOtpBtn!!.isActivated=true
                }
            }

            override fun onFailure(call: Call<SendOtpResponse>, t: Throwable) {
                verifyOtpBtn!!.isActivated=true

            }

        })
    }

    private fun setViewPhonenumber(){
        verifyOtpMobileNumber!!.text= PHONE_NUMBER
    }
    private fun initEditTexts(){
        listOfet= listOf(
            otpCode1,
            otpCode2,
            otpCode3,
            otpCode4,
            otpCode5,
            otpCode6) as List<EditText>

        sizeofets=listOfet.size

        for (idx in 0 until sizeofets-1){
            listOfet[idx].addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(s.toString().trim().isNotEmpty()){
                        listOfet[idx+1].requestFocus()
                    }
                }
            })
        }
    }

    private fun getInput():String{
        var result=""
        for (idx in 0 until sizeofets){
            result+=listOfet[idx].text.toString()
        }
        return result
    }

    private fun isVerifiedOTP(input:String, otp:String):Boolean{
        return input==otp
    }

    private fun initButtons(){
        verifyOtpBtn!!.setOnClickListener {
            apiGetOTP()
        }

        resendOtp!!.setOnClickListener {
            startActivity(Intent(this, SendOTP::class.java))
            finish()
        }

    }



}