package com.uday.mapsapp

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


class SMSFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_sms, container, false)
        val num =  view.findViewById<EditText>(R.id.smsText).text
        view.findViewById<Button>(R.id.smsSendButton).setOnClickListener { view ->
            Toast.makeText(context,"Clicked", Toast.LENGTH_LONG).show()
            sendSMS(num)
        }

        return view
    }


    fun sendSMS(num: Editable){
        when {
            context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.SEND_SMS) } == PackageManager.PERMISSION_GRANTED -> {
                // permission is granted
                Toast.makeText(context,num.toString(), Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("smsto:")  // This ensures only SMS apps respond
                    putExtra("address", num.toString())
                    putExtra("sms_body", "Your Address is: " + MainActivity.address + " Your latitude is: " + MainActivity.loc.latitude + " Your longitude is: " + MainActivity.loc.longitude)
                }
                startActivity(Intent.createChooser(intent,"SMS"))
            }
            ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.SEND_SMS)-> {
                // additional rationale is displayed
                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
            }
            else-> {
                // permission has not been asked yet
                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
            }
        }

    }
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result:Boolean ->
        // checking the result of permission
        if (result) {
            Toast.makeText(context,getString(R.string.per_grant), Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,getString(R.string.per_not_grant), Toast.LENGTH_SHORT).show()
        }
    }
}