package com.example.final_project;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Activity;
import androidx.fragment.app.FragmentActivity;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.final_project.Model.Experience;
import com.example.final_project.Model.Skill;
import com.example.final_project.Model.UserProfile;
import com.example.final_project.Model.Video;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

// TODO: This is profile page fragment

import java.util.List;

// TODO: This is profile page fragment
public class ProfileFragment extends Fragment {
    private FragmentActivity myContext;

    private YouTubePlayer yPlayer;
    private YouTubePlayerSupportFragment yPlayerFragment;
    private static final String YoutubeDeveloperKey = "AIzaSyBykGuv9hX3xsNqxfqosWmP1IwT4bh9aEc";
    private ImageView profile_image;
    private TextView profile_name;
    private TextView profile_description;
    private ImageView edit_photo;

    private String mdescription;
    private UserProfile user;
    private String userName;


    private ImageView add_skill;
    private ImageView add_exp;
    private ImageView add_video;

    RecyclerView skill_recyclerView;
    SkillAdapter skill_adapter;

    RecyclerView exp_recyclerView;
    ExperienceAdapter exp_adapter;

    RecyclerView video_recyclerView;
    VideoAdapter video_adapter;

    List<Skill> skillList;
    List<Experience> expList;
    List<Video> videoList;
    @Override
    public void onAttach(Activity activity) {

        if (activity instanceof FragmentActivity) {
            myContext = (FragmentActivity) activity;
        }

        super.onAttach(activity);
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        final String videoID = "FeDQSP0V0JE";

        Bundle bundle = this.getArguments();
        if (bundle != null){
            user = bundle.getParcelable("user");
        }
        else {

        }

        user = UserProfile.getUserInstance();

        profile_image = view.findViewById(R.id.profile_image);
        profile_name = view.findViewById(R.id.profile_name);
        profile_description = view.findViewById(R.id.profile_description);
        add_skill = view.findViewById(R.id.add_skill);
        add_exp = view.findViewById(R.id.add_exp);
        add_video = view.findViewById(R.id.add_video);

        edit_photo = view.findViewById(R.id.edit);

        add_skill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetDialog = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                bundle.putString("TAG", "skill");
                bottomSheetDialog.setArguments(bundle);
                bottomSheetDialog.show(getFragmentManager(), bottomSheetDialog.getTag());
            }
        });

        add_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetDialog = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                bundle.putString("TAG","experience");
                bottomSheetDialog.setArguments(bundle);
                bottomSheetDialog.show(getFragmentManager(), bottomSheetDialog.getTag());
            }
        });

        edit_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetDialog = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                bundle.putString("TAG", "photo");
                bottomSheetDialog.setArguments(bundle);
                bottomSheetDialog.show(getFragmentManager(), bottomSheetDialog.getTag());

            }
        });

        add_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetDialog = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                bundle.putString("TAG","video");
                bottomSheetDialog.setArguments(bundle);
                bottomSheetDialog.show(getFragmentManager(), bottomSheetDialog.getTag());
            }
        });
        profile_name.setText(user.getName());
        profile_description.setText(user.getDescription());
        skillList = user.getSkillset();
        expList = user.getExpset();
        videoList = user.getVideoset();


        skill_recyclerView =  view.findViewById(R.id.skill_recycler);
        skill_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        skill_adapter = new SkillAdapter(getActivity(), skillList);
        skill_recyclerView.setAdapter(skill_adapter);


        exp_recyclerView = view.findViewById(R.id.exp_recycler);
        exp_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        exp_adapter = new ExperienceAdapter(getActivity(), expList);
        exp_recyclerView.setAdapter(exp_adapter);

        video_recyclerView = view.findViewById(R.id.video_recycler);
        video_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        video_adapter = new VideoAdapter(getActivity(), videoList);
        video_recyclerView.setAdapter(video_adapter);

        return view;
    }




}
