package com.charging.userregistration

import android.content.DialogInterface
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        ItemTouchHelper(object:
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id = userAdapter.getUserId(viewHolder.adapterPosition)
                myReference.child(id).removeValue()
                Toast.makeText(applicationContext, "User Deleted", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(binding.recyclerView)


        retrieveData()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete_all,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll){
            showDialogMessage()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showDialogMessage(){
        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete all Users")
        dialogMessage.setMessage("If click Yes, all users will be deleted." +
                "If you want to delete a specific user, you can swipe the item left or right that you want to delete.")
        dialogMessage.setNegativeButton("Cancel",DialogInterface.OnClickListener{ dialogInterface,i->
            dialogInterface.cancel()
        })
        dialogMessage.setPositiveButton("Yes",DialogInterface.OnClickListener{ dialogInterface,i->
            myReference.removeValue().addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    userAdapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext, "All users deleted...", Toast.LENGTH_SHORT).show()
                }
            }
        })

        dialogMessage.create().show()

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