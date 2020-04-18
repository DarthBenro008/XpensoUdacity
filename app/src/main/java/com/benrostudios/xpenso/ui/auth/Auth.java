package com.benrostudios.xpenso.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.benrostudios.xpenso.MainActivity;
import com.benrostudios.xpenso.R;
import com.benrostudios.xpenso.ui.auth.signin.SignIn;
import com.benrostudios.xpenso.ui.auth.signup.SignUp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Auth extends AppCompatActivity implements SignUp.SwitchToSignIn, SignIn.SwitchToSignUp {
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        replaceFragment(new SignIn());
    }

    public void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.auth_container,fragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI();
        }
    }

    @Override
    public void switchtosignup() {
        replaceFragment(new SignUp());
    }

    @Override
    public void switchtosignin() {
        replaceFragment(new SignIn());
    }


    @Override
    public void authenticated() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Event.LOGIN,"loggedin");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN,bundle);
        updateUI();
    }

    public void updateUI(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
