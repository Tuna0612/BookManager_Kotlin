package com.tuna.bookmanager.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.tuna.bookmanager.R
import com.tuna.bookmanager.adapter.ProfileAdapter
import com.tuna.bookmanager.database.ProfileDAO
import com.tuna.bookmanager.model.Profile
import kotlinx.android.synthetic.main.dialog_add_user.*
import kotlinx.android.synthetic.main.dialog_add_user.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.util.ArrayList

class ProfileFragment : Fragment() {
    var listProfile: ArrayList<Profile> = ArrayList()
    private lateinit var profileDAO: ProfileDAO
    private lateinit var adapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.fragment_profile, container, false)
        profileDAO = ProfileDAO(view.context)
        listProfile = profileDAO.getAllProfile
        view.rcViewProfile.layoutManager = LinearLayoutManager(context)
        adapter = ProfileAdapter(listProfile, context, object : ProfileAdapter.AdapterListener {
            override fun OnClick(position: Int) {

            }
        })

        view.fab.setOnClickListener {
            val view: View = LayoutInflater.from(activity).inflate(R.layout.dialog_add_user, null)
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Add User")
            builder.setIcon(R.drawable.ic_note_add)
            builder.setView(view)
            //validate  Form()

            val username = view.edUserdialog.text
            val name = view.edNamedialog.text
            val password = view.edPassdialog.text
            val confirmpass = view.edConfirmPassdialog.text
            val phone = view.edPhonedialog.text
            val dialog: AlertDialog = builder.create()
            dialog.show()
            view.cardsave.setOnClickListener {
                //if(validateForm()>0){
                val profile = Profile(
                    username.toString(),
                    name.toString(),
                    password.toString(),
                    phone.toString()
                )
                profileDAO.addProfile(profile)
                listProfile.clear()
                listProfile = profileDAO.getAllProfile
                adapter.changeDataset(listProfile)
                dialog.dismiss()

            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        view!!.rcViewProfile.adapter = adapter
        listProfile.clear()
        listProfile = profileDAO.getAllProfile
        adapter.changeDataset(listProfile)
    }

    private fun validateForm(): Int {
        var check = 1
        when {
            edUserdialog.text.toString().isEmpty() -> {
                edUserdialog.error = "Username không được để trống"
                check = -1
            }
            edUserdialog.text.toString().length > 50 -> {
                edUserdialog.error = "Username phải nhỏ hơn 50 ký tự"
                check = -1
            }
            edNamedialog.text.toString().isEmpty() -> {
                edNamedialog.error = "Tên không được để trống"
                check = -1
            }
            edNamedialog.text.toString().length > 50 -> {
                edNamedialog.error = "Tên phải nhỏ hơn 50 ký tự"
                check = -1
            }
            edPassdialog.text.toString().isEmpty() -> {
                edPassdialog.error = "Password không được để trống"
                check = -1
            }
            edPassdialog.text.toString().length > 50 -> {
                edPassdialog.error = "Password phải nhỏ hơn 50 ký tự"
                check = -1
            }
            (edConfirmPassdialog.text.toString()) != edPassdialog.text.toString() -> {
                edConfirmPassdialog.error = "Không khớp"
                check = -1
            }
            edPhonedialog.text.toString().isEmpty() -> {
                edPhonedialog.error = "SĐT không được để trống"
                check = -1
            }
        }
        return check
    }


}
