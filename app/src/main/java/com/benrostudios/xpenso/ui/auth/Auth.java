package com.benrostudios.xpenso.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.benrostudios.xpenso.ui.MainActivity;
import com.benrostudios.xpenso.R;
import com.benrostudios.xpenso.ui.auth.income.IncomeFragment;
import com.benrostudios.xpenso.ui.auth.signin.SignIn;
import com.benrostudios.xpenso.ui.auth.signup.SignUp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Auth extends AppCompatActivity implements SignUp.SwitchToSignIn, SignIn.SwitchToSignUp, IncomeFragment.SetupCall {
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        replaceFragment(new SignIn());
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.auth_container, fragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
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
        replaceFragment(new IncomeFragment());
    }

    public void updateUI() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setUpCompleted() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Event.LOGIN, "loggedin");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
        updateUI();
    }

}
