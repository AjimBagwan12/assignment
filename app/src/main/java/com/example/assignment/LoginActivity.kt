package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var verificationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.buttonSendOTP.setOnClickListener {
            val phoneNumber = binding.editTextPhoneNumber.text.toString().trim()

            // Implement Firebase phone authentication
            if (phoneNumber.isNotEmpty()) {
                sendOTP(phoneNumber)
            } else {
                Toast.makeText(this, "Enter a valid phone number", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonVerifyOTP.setOnClickListener {
            val otp = binding.editTextOTP.text.toString().trim()

            // Implement Firebase phone authentication
            if (otp.isNotEmpty()) {
                verifyOTP(otp)
            } else {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }

        // Add the rest of your code...
    }

    private fun sendOTP(phoneNumber: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            this,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(this@LoginActivity, "Verification Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(verificationId, token)
                    this@LoginActivity.verificationId = verificationId
                    Toast.makeText(this@LoginActivity, "Code Sent", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }


    private fun verifyOTP(otp: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Authentication successful, start the MainActivity
                    Toast.makeText(this@LoginActivity, "Authentication Successful", Toast.LENGTH_SHORT).show()
                    startMainActivity()
                } else {
                    Toast.makeText(this@LoginActivity, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Optional: Close the login activity to prevent going back with the back button
    }


}
