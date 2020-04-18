package com.benrostudios.xpenso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.benrostudios.xpenso.ui.MainActivity;
import com.benrostudios.xpenso.ui.auth.Auth;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 1000;
    private FirebaseAuth mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.FullScreenTheme);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        mFirebase = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if(mFirebase.getCurrentUser() != null){
                    i = new Intent(SplashScreen.this,
                            MainActivity.class);
                }else{
                    i = new Intent(SplashScreen.this,
                            Auth.class);
                }
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }

}

