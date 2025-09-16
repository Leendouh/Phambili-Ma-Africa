package com.example.phambili_ma_africa

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.edit

class Login : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordToggle: ImageView
    private lateinit var loginButton: Button
    private lateinit var rememberMe: CheckBox
    private lateinit var forgotPassword: TextView
    private lateinit var signUpLink: TextView

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        passwordToggle = findViewById(R.id.passwordToggle)
        loginButton = findViewById(R.id.loginButton)
        rememberMe = findViewById(R.id.rememberMe)
        forgotPassword = findViewById(R.id.forgotPassword)
        signUpLink = findViewById(R.id.signUpLink)

        val prefs: SharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE)

        // Load saved credentials if Remember Me is checked
        if (prefs.getBoolean("rememberMe", false)) {
            emailEditText.setText(prefs.getString("email", ""))
            passwordEditText.setText(prefs.getString("password", ""))
            rememberMe.isChecked = true
        }

        // Toggle password visibility
        passwordToggle.setOnClickListener {
            if (isPasswordVisible) {
                passwordEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordToggle.setImageResource(R.drawable.ic_eye) // open eye
                isPasswordVisible = false
            } else {
                passwordEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordToggle.setImageResource(R.drawable.ic_eye_off) // crossed eye
                isPasswordVisible = true
            }
            passwordEditText.setSelection(passwordEditText.text.length) // Keep cursor at end
        }

        // Login button click
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: Replace with Firebase/Auth API
                if (email == "test@example.com" && password == "123456") {
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                    // Save credentials if Remember Me is checked
                    if (rememberMe.isChecked) {
                        prefs.edit {
                            putString("email", email)
                                .putString("password", password)
                                .putBoolean("rememberMe", true)
                        }
                    } else {
                        prefs.edit { clear() }
                    }

                    // Navigate to HomeActivity
                    val intent = Intent(this, LandingPage ::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Forgot password
        forgotPassword.setOnClickListener {
            Toast.makeText(this, "Redirect to Forgot Password screen", Toast.LENGTH_SHORT).show()
            // startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        // Sign up link
        signUpLink.setOnClickListener {
            startActivity(Intent(this, Sign_Up::class.java))
        }
    }
}
