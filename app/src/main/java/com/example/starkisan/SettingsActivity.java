package com.example.starkisan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.Preference.OnPreferenceClickListener;
import androidx.preference.PreferenceFragmentCompat;
import com.example.starkisan.models.PreferenceModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    public static class PrefsFragment extends PreferenceFragmentCompat {
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.root_preferences);
            findPreference("sign_out").setOnPreferenceClickListener(new OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(PrefsFragment.this.getContext(), LoginPhoneActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    PrefsFragment.this.startActivity(intent);
                    return true;
                }
            });

            findPreference("seller_pref").setOnPreferenceClickListener(new OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    PrefsFragment.this.startActivity(new Intent(PrefsFragment.this.getContext(), SellerActivity.class));
                    return false;
                }
            });
            final EditTextPreference mandiPref = (EditTextPreference) findPreference("mandi_pref");
            final EditTextPreference commodityPref = (EditTextPreference) findPreference("commodity_pref");
            final EditTextPreference gradePref = (EditTextPreference) findPreference("grade_pref");
            mandiPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("preference_data").document("lists").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        public void onComplete(Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                PreferenceModel lists = (PreferenceModel) ((DocumentSnapshot) task.getResult()).toObject(PreferenceModel.class);
                                List<String> sourceList = lists.getSourceList();
                                sourceList.add(mandiPref.getText());
                                lists.setSourceList(sourceList);
                                db.collection("preference_data").document("lists").set(lists).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    public void onSuccess(Void aVoid) {
                                        Context context = PrefsFragment.this.getContext();
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(mandiPref.getText());
                                        sb.append(" added!");
                                        Toast.makeText(context, sb.toString(), 0).show();
                                    }
                                });
                                return;
                            }
                            Toast.makeText(PrefsFragment.this.getContext(), "Error! You do not have access.", 0).show();
                        }
                    });
                    mandiPref.setText("");
                    return true;
                }
            });
            commodityPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("preference_data").document("lists").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        public void onComplete(Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                PreferenceModel lists = (PreferenceModel) ((DocumentSnapshot) task.getResult()).toObject(PreferenceModel.class);
                                List<String> commodityList = lists.getCommodityList();
                                commodityList.add(commodityPref.getText());
                                lists.setCommodityList(commodityList);
                                db.collection("preference_data").document("lists").set(lists).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    public void onSuccess(Void aVoid) {
                                        Context context = PrefsFragment.this.getContext();
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(commodityPref.getText());
                                        sb.append(" added!");
                                        Toast.makeText(context, sb.toString(), 0).show();
                                    }
                                });
                                return;
                            }
                            Toast.makeText(PrefsFragment.this.getContext(), "Error! You do not have access.", 0).show();
                        }
                    });
                    commodityPref.setText("");
                    return true;
                }
            });
            gradePref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("preference_data").document("lists").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        public void onComplete(Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                PreferenceModel lists = (PreferenceModel) ((DocumentSnapshot) task.getResult()).toObject(PreferenceModel.class);
                                List<String> gradeList = lists.getGradeList();
                                gradeList.add(gradePref.getText());
                                lists.setGradeList(gradeList);
                                db.collection("preference_data").document("lists").set(lists).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    public void onSuccess(Void aVoid) {
                                        Context context = PrefsFragment.this.getContext();
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(gradePref.getText());
                                        sb.append(" added!");
                                        Toast.makeText(context, sb.toString(), 0).show();
                                    }
                                });
                                return;
                            }
                            Toast.makeText(PrefsFragment.this.getContext(), "Error! You do not have access.", 0).show();
                        }
                    });
                    gradePref.setText("");
                    return true;
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle((CharSequence) "Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction().replace(16908290, new PrefsFragment()).commit();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
