package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    lateinit var etEmail : EditText
    lateinit var etPass : EditText
    lateinit var btnLogin : Button
    lateinit var tvRedirectSignUp : TextView
    lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        tvRedirectSignUp = findViewById(R.id.tvRedirectSignUp)
        btnLogin = findViewById(R.id.btnLogin)
        etEmail = findViewById(R.id.etEmailAddress)
        etPass = findViewById(R.id.etPassword)

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            login()
        }
        tvRedirectSignUp.setOnClickListener {
            val i = Intent(this@Login , Register::class.java)
            startActivity(i)
            finish()
        }

    }

    private fun login() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                val i = Intent(this@Login , MainActivity ::class.java)
                startActivity(i)
                finish()

            } else
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
        }
    }

}