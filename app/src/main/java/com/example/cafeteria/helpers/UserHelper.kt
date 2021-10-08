package com.example.cafeteria.helpers

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.example.cafeteria.MIN_PASSWORD_LENGTH

object UserHelper {

    /**
     *To validate input data
     * */
    fun validateLoginData(_context: Context?, _email:EditText?, _password: EditText?):Boolean{
        when{
            _email?.text!!.trim().isEmpty() && _password?.text!!.isEmpty() -> {
                _email.error="Email cannot be empty!"
                _password.error="Password cannot be empty!"
                Toast.makeText(_context, "Please fill all the fields!", Toast.LENGTH_SHORT).show()
            }
            _email.text!!.trim().isEmpty() -> {
                _email.error="Email cannot be empty!"
            }
            !Patterns.EMAIL_ADDRESS.matcher(_email.text).matches()->{
                _email.error = "Enter valid email."
            }
            _password?.text!!.isEmpty()->{
                _password.error="Password cannot be empty!"
            }
            _password.text!!.length < MIN_PASSWORD_LENGTH ->{
                _password.error="Password cannot be less than 8 characters."
            }
            else->{
                _email.error=null
                _password.error = null
                return true
            }
        }
        return false
    }

    /**
     *To validate input Register data
     * */
    fun validateRegisterData(_context: Context?,_name:EditText?, _email:EditText?, _password: EditText?,_confirmPass:EditText?, _mobilePhone:EditText?):Boolean{
        when{
            _email?.text!!.trim().isEmpty() && _password?.text!!.isEmpty() && _confirmPass?.text!!.isEmpty()  && _name?.text!!.trim().isEmpty() && _mobilePhone?.text!!.trim().isEmpty()-> {
                _name.error= "Name cannot be empty!"
                _email.error="Email cannot be empty!"
                _password.error="Password cannot be empty!"
                _confirmPass.error = "Password cannot be empty!"
                _mobilePhone.error = "Mobile number cannot be empty!"

                Toast.makeText(_context, "Please fill all the fields!", Toast.LENGTH_SHORT).show()
            }
            _name?.text!!.trim().isEmpty() -> {
                _name.error ="Name cannot be empty!"
            }
            _email.text!!.trim().isEmpty() -> {
                _email.error="Email cannot be empty!"
            }
            !Patterns.EMAIL_ADDRESS.matcher(_email.text).matches()->{
                _email.error = "Enter valid email."
            }
            _password?.text!!.isEmpty()->{
                _password.error="Password cannot be empty!"
            }
            _confirmPass?.text!!.isEmpty()->{
                _password.error="Password cannot be empty!"
            }
            _password.text!!.length < MIN_PASSWORD_LENGTH ->{
                _password.error="Password cannot be less than 8 characters."
            }
            _confirmPass.text!!.length < MIN_PASSWORD_LENGTH ->{
                _confirmPass.error="Password cannot be less than 8 characters."
            }

            _mobilePhone?.text!!.length < 11 -> {
                _mobilePhone.error = "mobile number is invalid"
            }
            else->{
                _name.error=null
                _email.error=null
                _password.error = null
                _confirmPass.error= null
                _mobilePhone.error= null
                return true
            }
        }
        return false
    }





}