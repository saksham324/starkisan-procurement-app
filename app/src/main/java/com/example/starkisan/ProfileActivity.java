package com.example.starkisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.starkisan.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
    private Button btnSave;
    private EditText mEditTextName;
    private TextView mTextVewName;
    OnClickListener onBtnSaveClick = new OnClickListener() {
        public void onClick(View view) {
            ProfileActivity.this.saveUserInformation();
            ProfileActivity profileActivity = ProfileActivity.this;
            profileActivity.startActivity(new Intent(profileActivity, MainActivity.class));
        }
    };

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_profile);
        this.btnSave = (Button) findViewById(R.id.btnSaveButton);
        this.mEditTextName = (EditText) findViewById(R.id.EditTextName);
        this.mTextVewName = (TextView) findViewById(R.id.TextViewName);
        this.btnSave.setOnClickListener(this.onBtnSaveClick);
    }

    /* access modifiers changed from: private */
    public void saveUserInformation() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("users").document(user.getUid()).set(new UserModel(this.mEditTextName.getText().toString(), Boolean.valueOf(false), user.getUid()));
    }
}
