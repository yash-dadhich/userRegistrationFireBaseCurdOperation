package com.charging.userregistration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.charging.userregistration.databinding.ActivityAddUserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddUserActivity : AppCompatActivity() {
    lateinit var addUserBinding:ActivityAddUserBinding

    val database:FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference:DatabaseReference = database.reference.child("MyUsers")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addUserBinding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(addUserBinding.root)

        supportActionBar?.title = "Add User"

        addUserBinding.btnAddUser.setOnClickListener {
            addUserToDatabase()
        }
    }

    private fun addUserToDatabase(){
        val name : String = addUserBinding.etName.text.toString()
        val age : Int = addUserBinding.etAge.text.toString().toInt()
        val email :String = addUserBinding.etEmail.text.toString()

        val id :String = myReference.push().key.toString()

        val user = Users(id,name,age,email)
        myReference.child(id).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(applicationContext, "User successfully added to database", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(applicationContext, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}