package com.example.final_project;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.final_project.Model.UserProfile;


public class BasicActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    private UserProfile user;
    private Fragment fragment;
    BottomNavigationView navigation;
    private String eml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        navigation = (BottomNavigationView) findViewById(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();
        eml = getIntent().getStringExtra("email");

        if (bundle != null){
            user = bundle.getParcelable("user");
            fragment = new ProfileFragment();
            Bundle bdle = new Bundle();
            bdle.putParcelable("user", user);
            fragment.setArguments(bdle);
            loadFragment(2);

        }else{
            user = null;
            fragment = new PostsFeedFragment();
            loadFragment(0);

        }
        if (eml != null) {
            user = UserProfile.getInstance();
            //TODO:change the getInstance() to be getInstqnce(eml), then it may can load the user
        } else {
            user = UserProfile.getInstance();
        }
    }
    private boolean loadFragment(Integer num) {
        if(fragment != null) {
            navigation.getMenu().getItem(num).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

            return true;
        }
        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int current_index = 0;
        switch(menuItem.getItemId()) {
            case R.id.navigation_home:
                if (!(fragment instanceof PostsFeedFragment)) {
                    fragment = new PostsFeedFragment();
                    current_index = 0;
                }

                break;
            case R.id.navigation_events:
                if (!(fragment instanceof EventsFeedFragment)) {
                    fragment = new EventsFeedFragment();
                    current_index = 1;
                }

                break;
            case R.id.navigation_profile:
                if (!(fragment instanceof ProfileFragment) || user == null) {
                    fragment = new ProfileFragment();
                    current_index = 2;
                } else {
                    ProfileFragment profile = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user", user);
                    profile.setArguments(bundle);
                    current_index = 2;
                }


                break;
        }
        return loadFragment(current_index);
    }
}
