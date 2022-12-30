package com.charging.userregistration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.charging.userregistration.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    lateinit var signInBinding : ActivitySignInBinding
    val auth:FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(signInBinding.root)

        supportActionBar?.title = "Sign In"

        signInBinding.txtSignUp.setOnClickListener {
            startActivity(Intent(this@SignInActivity,SignUpActivity::class.java))
        }

        signInBinding.btnSignIn.setOnClickListener {
            val userEmail = signInBinding.etEmail.text.toString()
            val userPassword = signInBinding.etPassword.text.toString()
            signInWithFirebase(userEmail,userPassword)
        //            startActivity(Intent(this@SignInActivity,MainActivity::class.java))
        }
    }

    private fun signInWithFirebase(userEmail:String,userPassword:String){
        auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener { task ->
            if (task.isSuccessful){
                startActivity(Intent(this@SignInActivity,MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(applicationContext, task.exception.toString(), Toast.LENGTH_SHORT).show()
                Log.d("error",task.exception.toString())
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val user =  auth.currentUser
        if (user!= null){
            startActivity(Intent(this@SignInActivity,MainActivity::class.java))
            finish()
        }
    }
}