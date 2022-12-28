package com.charging.userregistration

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.charging.userregistration.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val userList = ArrayList<Users>()
    lateinit var userAdapter : UsersAdapter


    val database:FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference:DatabaseReference = database.reference.child("MyUsers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.fab.setOnClickListener {
            val intent = Intent(this@MainActivity,AddUserActivity::class.java)
            startActivity(intent)
        }
        retrieveData()

    }

    private fun retrieveData(){
        myReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (eachUser in snapshot.children){
                    val user = eachUser.getValue(Users::class.java)
                    if (user!=null){
//                        Toast.makeText(applicationContext, "data Retrieved", Toast.LENGTH_SHORT).show()
                        userList.add(user)
                    }
                    userAdapter = UsersAdapter(this@MainActivity,userList)

                    binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                    binding.recyclerView.adapter = userAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}