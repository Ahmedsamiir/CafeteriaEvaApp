package com.example.cafeteria.activities

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.cafeteria.*
import com.example.cafeteria.models.ResetPasswordRequest
import com.example.cafeteria.models.ResetPasswordResponse
import com.example.cafeteria.services.ApiClient
import com.example.cafeteria.services.ResetPasswordService
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPassActivity : AppCompatActivity() {

    var etTextInputLayoutPass :TextInputEditText? = null
    var textInputLayoutPass : TextInputEditText? = null
    var etNewConfirmPass: TextInputEditText?= null
    var textInputLayoutConfirmPass:TextInputEditText?=null
    var updateBtn : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pass)

        etTextInputLayoutPass = findViewById(R.id.et_newpassword_enterpass)
        textInputLayoutPass = findViewById(R.id.text_inputlayout_pass)
        etNewConfirmPass = findViewById(R.id.et_newpassword_confirmpass)
        textInputLayoutConfirmPass = findViewById(R.id.text_inputlayout_confirmpass)
        updateBtn = findViewById(R.id.updateBtn)
    }

    private fun initEditTexts(){
        etTextInputLayoutPass!!.doOnTextChanged { text, start, before, count ->
            if(text!!.length< MIN_PASSWORD_LENGTH){
                textInputLayoutPass!!.error= "Minimum length for password is $MIN_PASSWORD_LENGTH"
            }else{
                textInputLayoutPass!!.error=null
            }
        }

        etConfirmPass!!.doOnTextChanged { text, start, before, count ->
            if(text.toString() != etTextInputLayoutPass!!.text.toString()){
                textInputLayoutConfirmPass!!.error="Password doesn't match the field above"
            }else{
                textInputLayoutConfirmPass!!.error=null
            }
        }
    }

    private fun initButtons(){
        updateBtn!!.setOnClickListener {
            updatePassword()
        }
    }

    private fun isNotValid(str1:String, str2:String, len:Int):Boolean{
        return (str1.length < len) || (str1!=str2)
    }

    private fun updatePassword(){

        if(isNotValid(etTextInputLayoutPass!!.text.toString(),
                etNewConfirmPass!!.text.toString(), MIN_PASSWORD_LENGTH ) )
            return //breaks the function

        callApi()

    }

    //to phone response from last page use if needed:
    private fun getPhoneResponse():String?{
        val bundle:Bundle? = intent.extras
        if(bundle?.containsKey(PHONE_RESPONSE)!!) {
            return intent.extras?.get(USER_DATA) as String
        }
        return null
    }

    //call api
    private fun callApi(){


        updateBtn!!.isActivated=false

        //api call goes here to save password
        val phoneNumber = getPhoneResponse()
        val resetPasswordRequest = ResetPasswordRequest(
            etTextInputLayoutPass!!.text.toString(),
            phoneNumber!!
        )
        val resetPasswordService: ResetPasswordService = ApiClient(this@ResetPassActivity).buildService(ResetPasswordService::class.java)
        val requestCall : Call<ResetPasswordResponse> = resetPasswordService.resetPassword(resetPasswordRequest)
        requestCall.enqueue(object : Callback<ResetPasswordResponse> {
            override fun onResponse(call: Call<ResetPasswordResponse>, response: Response<ResetPasswordResponse>) {
                if(response.isSuccessful){

                    updateBtn!!.isActivated=true
                    if(response.body()!!.isChanged){
                        Toast.makeText(this@ResetPassActivity, "Password changed successfully, please login", Toast.LENGTH_LONG).show()
                    }else{

                        Toast.makeText(this@ResetPassActivity, "Wrong password format", Toast.LENGTH_LONG).show()
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

                    updateBtn!!.isActivated=true

                    Toast.makeText(this@ResetPassActivity, "ERROR!", Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<ResetPasswordResponse>, t: Throwable) {
                updateBtn!!.isActivated=true
                Toast.makeText(this@ResetPassActivity, "ERROR!", Toast.LENGTH_LONG).show()
            }

        })
    }


}