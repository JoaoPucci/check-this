package tech.pucci.checkthis.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import tech.pucci.checkthis.R
import tech.pucci.checkthis.model.Person
import tech.pucci.checkthis.util.SharedPrefsUtil


class UserActivity : AppCompatActivity() {

    private lateinit var tvUserName: TextView
    private lateinit var tvUserEmail: TextView

    private lateinit var ilUserName: TextInputLayout
    private lateinit var ilUserEmail: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val btnLogin = findViewById<Button>(R.id.button_login)
        tvUserName = findViewById<Button>(R.id.user_name)
        tvUserEmail = findViewById<Button>(R.id.user_email)

        ilUserName = findViewById(R.id.input_layout_user_name)
        ilUserEmail = findViewById(R.id.input_layout_user_email)

        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        if (!validateFields()) return

        try {
            SharedPrefsUtil(this).savePerson(
                Person(
                    "",
                    "",
                    tvUserName.text.toString(),
                    tvUserEmail.text.toString(),
                    "pic"
                )
            )
            startActivity(Intent(this, EventsActivity::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_LONG).show()
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (tvUserName.text.isBlank()) {
            ilUserName.isErrorEnabled = true
            ilUserName.error = getString(R.string.mandatory_field)
            isValid = false
        } else {
            ilUserName.isErrorEnabled = false
        }

        if (tvUserEmail.text.isBlank()) {
            ilUserEmail.isErrorEnabled = true
            ilUserEmail.error = getString(R.string.mandatory_field)
            isValid = false
        } else if (tvUserEmail.text.split("@").size < 2) {
            ilUserEmail.isErrorEnabled = true
            ilUserEmail.error = getString(R.string.email_invalid)
            isValid = false
        } else {
            ilUserEmail.isErrorEnabled = false
        }

        return isValid
    }
}
