package com.example.final_project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.final_project.Model.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateAccountActivity extends AppCompatActivity {
    TextInputEditText firstname;
    TextInputEditText lastname;
    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText confirm;
    Button create;
    String eml;
    String pwd;
    String TAG;
    String first;
    String last;
    int num;
    JSONObject obj;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String js;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        TAG = "activity_create_account";
        firstname = findViewById(R.id.text_user_first_name);
        lastname = findViewById(R.id.text_user_last_name);
        email = findViewById(R.id.text_user_email);
        password = findViewById(R.id.text_user_password);
        confirm = findViewById(R.id.text_user_confirm_password);
        create = findViewById(R.id.create);
        mAuth = FirebaseAuth.getInstance();
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        num = 0;
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("PostsFeedFragment", "onAuthStateChanged:signed_in" + user.getUid());


                } else  {
                    Log.d("PostsFeedFragment", "onAuthStateChanged:signed_out");
                }
            }


        };
    }
    public void onClick(View v) {
        eml = email.getText().toString();
        pwd = password.getText().toString();
        first = firstname.getText().toString();
        last = lastname.getText().toString();
        String con_pwd = confirm.getText().toString();
        if (!pwd.equals(con_pwd)){
            Log.d(TAG, "passwords are not aligned.");
            return;
        }
        if (TextUtils.isEmpty(eml)){
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd) && pwd.length() < 6){
            Toast.makeText(this, "Password should be longer than 6 digits", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(eml, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //TODO: write the information into a json file


                            obj = new JSONObject();
                            try {
                                toJsonString(obj, eml, first, last);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.w(TAG,"json exception");
                            }
                            if (!isWriteStoragePermissionGranted()) {
                                Toast.makeText(CreateAccountActivity.this, "permission issue", Toast.LENGTH_SHORT).show();

                            }

                            UserProfile userProfile = UserProfile.getInstance();
                            userProfile.setName(first + " " + last);
                            Intent i = new Intent(CreateAccountActivity.this, LoginActivity.class);

                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }
    public void toJsonString(JSONObject obj, String user_email, String user_firstname, String user_lastname) throws JSONException {
        obj.put("email", user_email);
        obj.put("first name", user_firstname);
        obj.put("last name", user_lastname);


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    public void create(JSONObject obj) {
        try {
        File root = new File(Environment.getExternalStorageDirectory()+java.io.File.separator +"My Folder");//create a folder
        while (!root.exists()) {
            root.mkdirs();

        }

        String str = "myfile";
        String tonum = String.valueOf(num);
        str = str + tonum;

        File filepath = new File(root, str+".txt");
        while (!filepath.exists()){
            try {
                filepath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter writer = new FileWriter(filepath); // to write the file
        writer.append(obj.toString());
        writer.flush();
        writer.close();
        num++;
    } catch(IOException e)
    {
        e.printStackTrace();
    }
    }
    public void create_File( JSONObject obj) {
        if (!isWriteStoragePermissionGranted()) {
            Toast.makeText(this, "permission issue", Toast.LENGTH_SHORT).show();
            return;
        }
        create(obj);
    }
    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted2");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted2");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d(TAG, "External storage2");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                    create_File(obj);
                }else{
                    //
                }
                break;

            case 3:
                Log.d(TAG, "External storage1");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                    //SharePdfFile();
                }else{
                    //progress.dismiss();
                }
                break;
        }
    }


}
