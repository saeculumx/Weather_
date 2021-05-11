package com.example.weather_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //if (user != null) {
        //    Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
        //    //setContentView(R.layout.activity_main_weather_nav);
        //} else {
        //    singIn(findViewById(R.id.a).getRootView());
        //}

    }

    public void singIn(View v) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );
        Intent signInIntent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
        //setContentView(R.layout.activity_main_weather_nav);

    }

}