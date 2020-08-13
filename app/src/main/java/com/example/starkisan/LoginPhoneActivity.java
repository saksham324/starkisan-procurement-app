package com.example.starkisan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Arrays;

public class LoginPhoneActivity extends AppCompatActivity {
    private static int SIGN_IN_CODE = 0;
    public static final String TAG = "login_activity";

    private FirebaseFirestore db;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startLoginActivity();
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void startLoginActivity() {
        startActivityForResult(((AuthUI.SignInIntentBuilder)
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig[]{new AuthUI.IdpConfig.PhoneBuilder().setDefaultCountryIso("IN").build()}))).build(),
                SIGN_IN_CODE);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_CODE) {
            IdpResponse fromResultIntent = IdpResponse.fromResultIntent(data);
            if (resultCode == -1) {
                startActivity(new Intent(this, ProfileActivity.class));
                return;
            }
            startLoginActivity();
            Toast.makeText(this, "Error signing in", Toast.LENGTH_SHORT).show();
        }
    }
}
