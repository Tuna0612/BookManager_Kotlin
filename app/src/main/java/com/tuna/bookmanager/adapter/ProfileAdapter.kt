package com.tuna.bookmanager.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.tuna.bookmanager.R
import com.tuna.bookmanager.database.ProfileDAO
import com.tuna.bookmanager.model.Profile
import kotlinx.android.synthetic.main.dialog_edit_profile.view.*
import kotlinx.android.synthetic.main.item_profile.view.*

class ProfileAdapter(
    var listProfile: List<Profile>,
    val context: Context?,
    private val adapterListener: AdapterListener
) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    interface AdapterListener {
        fun OnClick(position: Int)
    }

    var profileDAO = ProfileDAO(context)

    fun changeDataset(listProfile: List<Profile>) {
        this.listProfile = listProfile
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_profile,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listProfile.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvUsername.text = "Username: " + listProfile[position].username
        holder.tvName.text = "Tên: " + listProfile[position].name
        holder.tvPhone.text = "SĐT: " + listProfile[position].phone
        holder.cardView.setOnClickListener { adapterListener.OnClick(position) }
        holder.btnEdit.setOnClickListener {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.dialog_edit_profile, null)
            val builder = AlertDialog.Builder(context)
            builder.setView(view)
            builder.setTitle("Edit Profile")
            builder.setIcon(R.drawable.ic_edit)
            val dialog = builder.create()
            dialog.show()

            view.edName.setText(listProfile[position].name)
            view.edPhone.setText(listProfile[position].phone)

            view.cardsave.setOnClickListener {
                profileDAO.updateProfile(
                    listProfile[position].username.toString(),
                    view.edName.text.toString(),
                    view.edPhone.text.toString()
                )
                listProfile = profileDAO.getAllProfile
                notifyDataSetChanged()
                dialog.dismiss()
            }


        }

        holder.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setIcon(R.drawable.ic_delete)
            builder.setTitle("Delete Profile")
            builder.setMessage("Do you want to delete profile: " + listProfile[position].name)
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                profileDAO.deleteProfile(listProfile[position].username.toString())
                listProfile = profileDAO.getAllProfile
                notifyDataSetChanged()
            })

            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
            })

            val dialog = builder.create()
            dialog.show()


        }
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvUsername: TextView = itemview.tvUsername
        val tvName: TextView = itemview.tvName
        val tvPhone: TextView = itemview.tvPhone
        val cardView: CardView = itemView.cardView
        val btnEdit: ImageView = itemview.btnEdit
        val btnDelete: ImageView = itemview.btnDelete
    }

}