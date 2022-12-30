package com.charging.userregistration

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import com.charging.userregistration.databinding.ActivitySignInBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    lateinit var signInBinding: ActivitySignInBinding
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(signInBinding.root)

        supportActionBar?.title = "Sign In"

        signInBinding.txtSignUp.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }

        signInBinding.btnSignIn.setOnClickListener {
            val userEmail = signInBinding.etEmail.text.toString()
            val userPassword = signInBinding.etPassword.text.toString()
            signInWithFirebase(userEmail, userPassword)
            //            startActivity(Intent(this@SignInActivity,MainActivity::class.java))
        }

        signInBinding.txtForgotPassword.setOnClickListener {
            showDialog()
        }
    }

    private fun signInWithFirebase(userEmail: String, userPassword: String) {
        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(applicationContext, task.exception.toString(), Toast.LENGTH_SHORT)
                    .show()
                Log.d("error", task.exception.toString())
            }
        }
    }

  /*  private fun showDialogMessage() {
        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete all Users")
        dialogMessage.setMessage(
            "If click Yes, all users will be deleted." +
                    "If you want to delete a specific user, you can swipe the item left or right that you want to delete."
        )
        dialogMessage.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
        dialogMessage.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialogInterface, i ->

            })
    }*/

    private fun showDialog() {
        val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_forgot_password)
        val resendBtn = dialog.findViewById(R.id.btn_resend_email) as Button
        resendBtn.setOnClickListener {
            val etEmail = dialog.findViewById(R.id.etEmail) as EditText
            val email = etEmail.text.toString()
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    dialog.dismiss()
                    Toast.makeText(
                        applicationContext,
                        "Password reset email has been sent to above email address",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(     
                        applicationContext,
                        task.exception?.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        dialog.show()
    }



}