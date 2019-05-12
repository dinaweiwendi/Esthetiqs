package com.example.final_project;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.final_project.Model.UserProfile;


public class BottomSheetFragment extends BottomSheetDialogFragment {

    Button button;
    EditText text;
    UserProfile user;
    String tag;
    TextView title;
    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sheet, null);
        dialog.setContentView(contentView);

        Bundle bundle = this.getArguments();
        if (bundle != null){
            user = bundle.getParcelable("user");
            tag = bundle.getString("TAG");

        }
        button = contentView.findViewById(R.id.button);
        text = contentView.findViewById(R.id.content);
        title = contentView.findViewById(R.id.textView);
        user = UserProfile.getInstance();
        switch(tag) {
            case "photo":

                title.setText("Description");
                break;
            case "skill":

                title.setText("Skill");
                break;
            case "experience":
                title.setText("Experiment");
                break;
            case "video":

                title.setText("Video");
                break;

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    //TODO: from last fragment get TAG, add judgement to see which part is changed,  update profile

                    String change = text.getText().toString();
                    switch(tag) {
                        case "photo":

                            user.setDescription(change);
                            break;
                        case "skill":

                            user.setSkill(change);
                            break;
                        case "experience":
                            //TODO: set user's experiment
                            user.setExp(change);
                            break;
                        case "video":

                            user.setVid(change, "my video");
                            break;

                    }

                    ProfileFragment profile = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user", user);
                    profile.setArguments(bundle);

                moveToNewActivity(bundle);
            }
        });

    }
    private void moveToNewActivity (Bundle bundle) {

        Intent i = new Intent(getActivity(), BasicActivity.class);
        i.putExtras(bundle);
        startActivity(i);
        ( getActivity()).overridePendingTransition(0, 0);

    }
}
