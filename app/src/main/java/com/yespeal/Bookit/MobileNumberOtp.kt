package com.yespeal.Bookit

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.WindowManager
import android.widget.EditText
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.activity_mobile_number_otp.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.support.v4.content.ContextCompat.getSystemService
import com.google.firebase.auth.PhoneAuthProvider


class MobileNumberOtp : AppCompatActivity() {

    var ccp: CountryCodePicker? = null

    var editTextCarrierNumber: EditText? = null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_mobile_number_otp)

        ccp = findViewById(R.id.ccp);
        editTextCarrierNumber = this.findViewById(R.id.editText_carrierNumber);

        ccp!!.registerCarrierNumberEditText(editTextCarrierNumber);
        ccp!!.setPhoneNumberValidityChangeListener(CountryCodePicker.PhoneNumberValidityChangeListener {
            if(it)
            {
                txtNextOtp.setTextColor(getColor(R.color.next_Color))
                crdGetOtp.isClickable=true
                getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
            }
            else{
                txtNextOtp.setTextColor(getColor(R.color.next_hideColor))
                crdGetOtp.isClickable=false

                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }



        })

        crdGetOtp.setOnClickListener {
            var intent =Intent(this,EnterOtp::class.java)
            intent.putExtra("PhoneNumber",ccp!!.fullNumberWithPlus)
            startActivity(intent)
            finish()
        }
    }

}
