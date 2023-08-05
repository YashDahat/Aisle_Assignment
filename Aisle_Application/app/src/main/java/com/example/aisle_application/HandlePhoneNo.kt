package com.example.aisle_application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class HandlePhoneNo: AppCompatActivity() {
    private lateinit var countryCode_Ele: EditText;
    private lateinit var phoneNo_Ele: EditText;
    private lateinit var submit_Ele: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_phoneno_screen)

        countryCode_Ele = findViewById(R.id.editTextCountryCode);
        phoneNo_Ele = findViewById(R.id.editTextPhoneNumber)
        submit_Ele = findViewById(R.id.submitButton)
        submit_Ele.setOnClickListener{onButtonSubmitClicked()}
    }

    private fun onButtonSubmitClicked() {
        val countryCode = countryCode_Ele.text.toString();
        val phoneNo = phoneNo_Ele.text.toString();
        Log.d("Country Code:", countryCode);
        Log.d("Phone No:", phoneNo);
        //Call the Rest API
        //Process the rest API
        //if respone == 200 ok -> send the user to next page
        //else throw new Error
        val retrofit = Retrofit.Builder()
            .baseUrl("https://app.aisle.co/V1/").addConverterFactory(GsonConverterFactory.create())
            .build();
        val HandleAPIRequestService = retrofit.create(HandleAPIRequest::class.java);
        GlobalScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    HandleAPIRequestService.sendOTPToPhone(countryCode+phoneNo);
                }
                Log.d("Response:", response.toString());
                if(response.code() == 200){
                    //Send the user to the next screen
                    navigateToOTPScreen();
                }
            }catch (e: Exception){
                Log.e("Error", e.toString());
            }
        }
    }
    private fun navigateToOTPScreen(){
        val intent = Intent(this, HandleOTP::class.java);
        intent.putExtra("phoneNo",countryCode_Ele.text.toString() + phoneNo_Ele.text.toString());
        startActivity(intent);
    }
}