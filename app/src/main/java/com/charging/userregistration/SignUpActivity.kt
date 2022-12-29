package com.charging.userregistration

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.charging.userregistration.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth



class SignUpActivity : AppCompatActivity() {

    lateinit var signupBinding: ActivitySignUpBinding
    val auth:FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signupBinding.root)


        signupBinding.btnSignIn.setOnClickListener {
            val userEmail = signupBinding.etEmail.text.toString()
            val userPassword = signupBinding.etPassword.text.toString()

            signUpWithFirebase(userEmail,userPassword)
        }

    }

    private fun signUpWithFirebase(userEmail:String, userPassword:String){
        auth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(applicationContext, "Your account is created successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(applicationContext, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}