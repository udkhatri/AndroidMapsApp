package com.uday.mapsapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class EmailFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_email, container, false)
        val emailId =  view.findViewById<EditText>(R.id.mailText).text
        view.findViewById<Button>(R.id.emailSendBtn).setOnClickListener { view ->
            Toast.makeText(context,"Clicked",Toast.LENGTH_LONG).show()
            sendEmail(emailId)
        }

        // Return the fragment view/layout
        return view


    }

    fun sendEmail(emailId: Editable){
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailId.toString()))
        i.putExtra(Intent.EXTRA_SUBJECT, "Your Location Data")
        i.putExtra(Intent.EXTRA_TEXT, "Your Address is: " + MainActivity.address + " Your latitude is: " + MainActivity.loc.latitude + " Your longitude is: " + MainActivity.loc.longitude)

        try {
            startActivity(Intent.createChooser(i, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                context,
                "There are no email clients installed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}