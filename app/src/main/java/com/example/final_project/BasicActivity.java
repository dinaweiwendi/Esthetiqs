package com.example.final_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;

import com.example.final_project.Model.UserProfile;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BasicActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = BasicActivity.class.getSimpleName();
    private UserProfile user;
    private Fragment fragment;
    BottomNavigationView navigation;
    private String eml;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        navigation = (BottomNavigationView) findViewById(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_key_user_email), Context.MODE_PRIVATE);
        eml = sharedPref.getString(getString(R.string.user_email_key), "sample name");

        if (bundle != null){
            user = bundle.getParcelable("user");
            fragment = new ProfileFragment();
            Bundle bdle = new Bundle();
            bdle.putParcelable("user", user);
            fragment.setArguments(bdle);
            loadFragment(2);

        }else{
            user = UserProfile.getUserInstance();
            searchUser(eml);
            fragment = new PostsFeedFragment();
            loadFragment(0);
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

    public void searchUser(final String login_email) {
        mDatabase.child("Users").child(login_email.replaceAll("[^a-zA-Z0-9]", "_")).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                user.setName(userProfile.name);
                Log.i(TAG, "name: " + user.name);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
