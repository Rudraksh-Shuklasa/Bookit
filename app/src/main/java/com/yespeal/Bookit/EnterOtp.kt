package com.yespeal.Bookit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_enter_otp.*
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.TaskExecutors
import javax.xml.datatype.DatatypeConstants.SECONDS
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction
import android.support.design.widget.Snackbar
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import android.content.Intent
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import android.R.id.edit
import android.content.SharedPreferences.Editor
import android.content.SharedPreferences








class EnterOtp : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var mVerificationId: String? = null
    lateinit var pref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_otp)

         pref = applicationContext.getSharedPreferences("BookIt", 0)


        val PhoneNumber = intent.getStringExtra("PhoneNumber")
        txtWaitTextOtp.text="Please Wait.\nWe will suto verify the OTP sent \nto "+PhoneNumber

        mAuth = FirebaseAuth.getInstance();
        sendVerificationCode(PhoneNumber);
        var s=OtpNumber.text.toString();

        crdVarifyOtp.setOnClickListener {
            if((OtpNumber.text.toString().length) != 6)
            {
                Toast.makeText(this,"Please enter right otp",Toast.LENGTH_SHORT).show()

            }
            else{
                verifyVerificationCode(OtpNumber.text.toString())
                progressBar.visibility=View.VISIBLE
            }
        }


    }

    private fun sendVerificationCode(mobile: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mobile,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallbacks
        )
    }
     var mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            //Getting the code sent by SMS
            val code = phoneAuthCredential.smsCode

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                OtpNumber.setText(code)
                //verifying the code
                verifyVerificationCode(code)
                progressBar.visibility=View.VISIBLE
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@EnterOtp, e.message, Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(s: String?, forceResendingToken: PhoneAuthProvider.ForceResendingToken?) {
            super.onCodeSent(s, forceResendingToken)
            mVerificationId = s
            var mResendToken = forceResendingToken
        }
    }

    private fun verifyVerificationCode(code: String) {
        //creating the credential
        val credential = this!!.mVerificationId?.let { PhoneAuthProvider.getCredential(it, code) }

        //signing the user
        signInWithPhoneAuthCredential(credential!!)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this@EnterOtp,
                OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        val editor = pref.edit()
                        editor.putBoolean("isLogin", true);
                        editor.commit()
                        progressBar.visibility=View.GONE
                        //verification successful we will start the profile activity

                     var intent=Intent(this,Drawer::class.java)
                        startActivity(intent)
                        finish()

                    } else {

                        //verification unsuccessful.. display an error message

                        var message = "Somthing is wrong, we will fix it soon..."
                        progressBar.visibility=View.GONE

                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered..."
                        }
                        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()

                    }
                })
    }


    override fun onPause() {
        super.onPause()
        progressBar.visibility=View.GONE
    }


}
