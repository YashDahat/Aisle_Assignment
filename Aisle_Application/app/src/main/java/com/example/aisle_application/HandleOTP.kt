package com.example.aisle_application

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HandleOTP: AppCompatActivity() {
    private lateinit var otp_Ele: EditText
    private lateinit var submit_Ele: Button
    private lateinit var phoneNo: String
    private lateinit var phoneNoText: TextView
    private lateinit var editPhoneNo: ImageButton
    private lateinit var resendOTPLink: TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_otp_screen);
        otp_Ele = findViewById(R.id.editTextOTP);
        submit_Ele = findViewById(R.id.OTPSubmit);
        editPhoneNo = findViewById(R.id.EditPhoneNoButton)
        resendOTPLink = findViewById(R.id.resendOTPLink)

        if(intent.getStringExtra("phoneNo").toString().isNullOrEmpty()) {
            phoneNo = "000000000000";
        }else{
            phoneNo = intent.getStringExtra("phoneNo").toString();
        }
        phoneNoText = findViewById(R.id.editTextPhoneNumber);
        phoneNoText.text = phoneNo;
        editPhoneNo.setOnClickListener{navigateToEditPhoneNo()}
        submit_Ele.setOnClickListener{onButtonSubmitClicked()}
        resendOTPLink.setOnClickListener { resendOTP() }
    }

    private fun onButtonSubmitClicked(){
        val otp = otp_Ele.text.toString();
        var responseJson: Response<JsonObject>? = null;
        Log.d("OTP:", otp);
        val retrofit = Retrofit.Builder()
            .baseUrl("https://app.aisle.co/V1/").addConverterFactory(GsonConverterFactory.create())
            .build();
        val HandleAPIRequestService = retrofit.create(HandleAPIRequest::class.java);
        GlobalScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    HandleAPIRequestService.verifyOTP(phoneNo, otp);
                }
                responseJson = response;

            }catch (e: Exception){
                Log.e("Error", e.toString());
            }
        }
        Log.d("Response:", responseJson.toString());
        Log.d("token", responseJson?.body()?.get("token").toString().equals("null").toString())
        if(responseJson?.code() != 200 || responseJson?.body()?.get("token").toString() == "null"){
            //Send the user to the next screen
            //Show error message, that entered OTP is wrong
            Log.d("Debug","You are here 1")
            showErrorDialog(this@HandleOTP, "Entered OTP is wrong!");
        }else{
            Log.d("Debug","You are here 2")
            navigateToNotes(responseJson?.body()?.get("token").toString());
        }
    }
    private fun navigateToNotes(token:String){
        val intent = Intent(this, HandleNotes::class.java);
        intent.putExtra("token",token);
        startActivity(intent);
    }
    private fun navigateToEditPhoneNo(){
        val intent = Intent(this, HandlePhoneNo::class.java);
        startActivity(intent);
    }
    private fun resendOTP(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://app.aisle.co/V1/").addConverterFactory(GsonConverterFactory.create())
            .build();
        var responseJson : Response<JsonObject>? = null;
        val HandleAPIRequestService = retrofit.create(HandleAPIRequest::class.java);
        GlobalScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    HandleAPIRequestService.sendOTPToPhone(phoneNo);
                }
                Log.d("Response:", response.toString());
                if(response.code() == 200){
                    //Show notification
                    responseJson = response;
                }
            }catch (e: Exception){
                Log.e("Error", e.toString());
            }
        }
        
    }
    fun showErrorDialog(context: Context, errorMessage: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage(errorMessage)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }
    fun showSuccessDialog(context: Context, Message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Success")
        builder.setMessage(Message)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }
}