package com.charging.userregistration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.charging.userregistration.databinding.ActivityEditUserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditUserActivity : AppCompatActivity() {

    private lateinit var updateUserBinding:ActivityEditUserBinding

    private val database:FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myReference:DatabaseReference = database.reference.child("MyUsers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateUserBinding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(updateUserBinding.root)

        supportActionBar?.title = "Edit User"

        getAndSetData()

        updateUserBinding.btnUpdateUser.setOnClickListener {
            updateData()
        }

    }

    private fun getAndSetData(){

        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age",0).toString()
        val email = intent.getStringExtra("email")

        updateUserBinding.etName.setText(name)
        updateUserBinding.etAge.setText(age)
        updateUserBinding.etEmail.setText(email)



    }

    private fun updateData(){
        val updatedName = updateUserBinding.etName.text.toString()
        val updatedAge = updateUserBinding.etAge.text.toString().toInt()
        val updatedEmail = updateUserBinding.etEmail.text.toString()
        val id = intent.getStringExtra("id").toString()

        val userMap = mutableMapOf<String,Any>()
        userMap["userId"] = id
        userMap["userName"] = updatedName
        userMap["userAge"] = updatedAge
        userMap["userEmail"] = updatedEmail

        myReference.child(id).updateChildren(userMap).addOnCompleteListener { task -> 
            if (task.isSuccessful){
                Toast.makeText(applicationContext, "data updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(applicationContext, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }
}