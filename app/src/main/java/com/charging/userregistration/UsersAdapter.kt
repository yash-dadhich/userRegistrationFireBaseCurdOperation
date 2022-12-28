package com.charging.userregistration

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charging.userregistration.databinding.ListItemBinding

class UsersAdapter(val context: Context, val usersList: ArrayList<Users>) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    inner class UserViewHolder(val adapterBinding: ListItemBinding) :
        RecyclerView.ViewHolder(adapterBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.adapterBinding.userName.text = usersList[position].userName
        holder.adapterBinding.userAge.text = usersList[position].userAge.toString()
        holder.adapterBinding.userEmail.text = usersList[position].userEmail

        holder.adapterBinding.linearLayout.setOnClickListener {
            val intent = Intent(context,EditUserActivity::class.java)
            intent.putExtra("id",usersList[position].userId)
            intent.putExtra("name",usersList[position].userName)
            intent.putExtra("age",usersList[position].userAge)
            intent.putExtra("email",usersList[position].userEmail)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

}