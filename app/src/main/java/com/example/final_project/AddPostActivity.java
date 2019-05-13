package com.example.final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.final_project.Model.Post;
import com.example.final_project.Model.UserProfile;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddPostActivity extends AppCompatActivity {
    private static final String TAG = "AddPost";
    TextInputEditText mDescription;
    TextInputEditText mUrl;
    String description;
    String url;
    Button confirm;
    Post mPost;
    int mImage;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mDescription = findViewById(R.id.text_description);
        mUrl = findViewById(R.id.text_url);
        confirm = findViewById(R.id.button_confirm);
        mImage = R.drawable.default_profile;
        username = UserProfile.getUserInstance().name;

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pattern = "^.*((youtu.be\\/)|(v\\/)|(\\/u\\/\\w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
                Pattern r = Pattern.compile(pattern);
                String videoId = mUrl.getText().toString();
                // Now create matcher object.
                Matcher m = r.matcher(videoId);
                if (m.find()) {
                    videoId = m.group(7);
                    if(videoId.length() != 11) {
                        Toast.makeText(AddPostActivity.this, "The URL is invalid. Please try again!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else {
                    Toast.makeText(AddPostActivity.this, "The URL is invalid. Please try again!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //"<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/lseL2l-ZWM0\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>"
                String head = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/";
                String tail = "\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
                url = head+videoId+tail;
                Log.i("Video Url: ", url);

                description = mDescription.getText().toString();

                mPost = new Post(description ,mImage, username, url);
                Intent resultIntent = new Intent();
                resultIntent.putExtra(PostsFeedFragment.POST_ADDED, mPost);

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });




    }
}
