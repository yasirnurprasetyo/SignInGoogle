package com.yasir.signingoogle.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.yasir.signingoogle.MainActivity
import com.yasir.signingoogle.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var googleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupGoogleSignIn()
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        btnLoginGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun signIn(){
        val signInIntent =  googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, 1)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if(requestCode == 1){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                Toast.makeText(this, "Berhasil Login", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            }catch (e: ApiException){
                // Google Sign In failed, update UI appropriately
                Log.e("Error", "Google sign in failed ${e.message}")
            }
        }
    }
}