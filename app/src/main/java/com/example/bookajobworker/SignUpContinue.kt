package com.example.bookajobworker

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.widget.Button

class SignUpContinue : AppCompatActivity() {

    private lateinit var editBirthDate: EditText
    private lateinit var datePicker: DatePicker
    private lateinit var btnBack2: Button
    private lateinit var btnSignUp: Button

    // Fields Initialization
    private lateinit var txtfirstname: EditText
    private lateinit var txtmiddlename: EditText
    private lateinit var txtlastname: EditText
    private lateinit var txtsuffix: EditText
    // editBirthDate for the Birth Date
    private lateinit var txtgender: Spinner
    private lateinit var txtstreetadd: EditText
    private lateinit var txtcityadd: EditText
    //Registration Data Init
    private var registrationData: Map<String, Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_personal)

        val genderSpinner: Spinner = findViewById(R.id.genderSignIn)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.gender_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            genderSpinner.adapter = adapter
        }

        editBirthDate = findViewById(R.id.birthdateSignIn)
        datePicker = findViewById(R.id.datePicker)

        editBirthDate.setOnClickListener {
            showDatePickerDialog()
        }
        // Back to First Step
        btnBack2 = findViewById(R.id.buttonBack2)
        btnBack2.setOnClickListener {
            toFirstStep()
        }
        // Signing Up Fields are Complete
        btnSignUp = findViewById(R.id.buttonSignUp)
        btnSignUp.setOnClickListener {
            finishSignUp()
        }

        datePicker.init(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ) { view, year, monthOfYear, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, monthOfYear, dayOfMonth)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            editBirthDate.setText(sdf.format(selectedDate.time))
        }

        val bundle = intent.getBundleExtra("registration_data")
        registrationData = bundle?.let { convertBundleToMap(it) }

        // Find Fields by ID
        txtfirstname = findViewById(R.id.firstNameSignIn)
        txtmiddlename = findViewById(R.id.middleNameSignIn)
        txtlastname = findViewById(R.id.lastNameSignIn)
        txtsuffix = findViewById(R.id.suffixSignIn)
        txtgender = findViewById(R.id.genderSignIn)
        txtstreetadd = findViewById(R.id.streetAddressSignIn)
        txtcityadd = findViewById(R.id.cityAddressSignIn)
    }

    //Dialog Show
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                editBirthDate.setText(sdf.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun toFirstStep() {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
        finish()
    }

    private fun finishSignUp() {

        val firstname = txtfirstname.text.toString()
        val middlename = txtmiddlename.text.toString()
        val lastname = txtlastname.text.toString()
        val suffix = txtsuffix.text.toString()
        val birthdate = editBirthDate.text.toString()
        val gender = txtgender.selectedItem.toString()
        val staddress = txtstreetadd.text.toString()
        val ctaddress = txtcityadd.text.toString()
        val userrole = 1

        val newFields = mapOf(
            "firstname" to firstname,
            "middlename" to middlename,
            "lastname" to lastname,
            "suffix" to suffix,
            "selectedDate" to birthdate,
            "gender" to gender,
            "streetaddress" to staddress,
            "cityaddress" to ctaddress,
            "user_role" to userrole,
        )

        registrationData?.let { data ->
            val updatedRegistrationData = RegistrationData(data).withNewFields(newFields)
            showRegistrationDataDialog(this, updatedRegistrationData.data)
        }
    }

    private fun showRegistrationDataDialog(context: Context, registrationData: Map<String, Any>?) {
        val stringBuilder = StringBuilder()
        if (registrationData != null) {
            for ((key, value) in registrationData) {
                stringBuilder.append("$key: $value\n")
            }
        }
        val message = stringBuilder.toString().trim() // Trim to remove trailing newline

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Registration Data")
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            // Dismiss the dialog when the OK button is clicked
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun convertBundleToMap(bundle: Bundle): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        val keys = bundle.keySet()
        for (key in keys) {
            map[key] = bundle.get(key)!!
        }
        return map
    }
}
