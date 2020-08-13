package com.example.starkisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.starkisan.adapters.PageAdapter;
import com.example.starkisan.fragments.ExportFragment;
import com.example.starkisan.fragments.HistoryFragment;
import com.example.starkisan.fragments.StartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "activity_main";

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private BottomNavigationView mNav;
    private ViewPager2 mViewPager;
    OnNavigationItemSelectedListener onNavItemSelect = new OnNavigationItemSelectedListener() {
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navItemExport:
                    mViewPager.setCurrentItem(2);
                    return true;
                    
                case R.id.navItemHistory:
                    mViewPager.setCurrentItem(1);
                    return true;
                    
                case R.id.navItemStart:
                    mViewPager.setCurrentItem(0);
                    return true;
                    
                default:
                    return false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("StarKisan");
        mViewPager = (ViewPager2) findViewById(R.id.viewPager);
        mNav = (BottomNavigationView) findViewById(R.id.bottomNav);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new StartFragment());
        fragments.add(new HistoryFragment());
        fragments.add(new ExportFragment());

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), getLifecycle(), fragments);

        mViewPager.setAdapter(pageAdapter);
        mNav.setOnNavigationItemSelectedListener(onNavItemSelect);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.menuitem_settings) {
            return super.onOptionsItemSelected(item);
        }
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        return true;
    }
}
