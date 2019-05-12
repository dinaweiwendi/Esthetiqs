package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project.Model.Event;
import com.example.final_project.Model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PostsFeedFragment extends Fragment {

    private static final String TAG = EventsFeedFragment.class.getSimpleName();

    List<Post> mPosts;
    public static final String POST_ADDED = "com.example.final_proj.added_post";
    static final int ADD_POST_REQUEST = 1;
    PostAdapter mAdapter;
    RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post_feed, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mPosts = new ArrayList<>();
//        loadPosts();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.post_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPosts.add(
                new Post(

                        "Landed my triple pirouette today. Can't wait to show it off at the next showcase!",
                        R.drawable.prof3,
                        "Rogan Walter",
                        "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/DE570ecHmSw\" frameborder=\"0\" allowfullscreen></iframe>"));
        mPosts.add(
                new Post(

                        "Man, I am so exhausted from training at Millenium today!",
                        R.drawable.prof1,
                        "Ashlea Chandler",
                        "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/lToFklcBbI4\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>"));
        mPosts.add(
                new Post(

                        "Everyone HAS to check out Ashley Worley's class",
                        R.drawable.prof2,
                        "Romany Paul",
                        "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/lseL2l-ZWM0\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>"));

        //can hardcode in new posts here!
        mAdapter = new PostAdapter(getActivity(), mPosts);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPostIntent = new Intent(getContext(), AddPostActivity.class);
                startActivityForResult(addPostIntent, ADD_POST_REQUEST);
            }
        });

        return view;
    }

    private void loadPosts() {
        mDatabase.child("Posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Post tmp = singleSnapshot.getValue(Post.class);
                    mPosts.add(singleSnapshot.getValue(Post.class));
                }
                Log.i(TAG, mPosts.isEmpty()+"");
                mAdapter = new PostAdapter(getActivity(), mPosts);
                mRecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_POST_REQUEST) {
            if (resultCode == RESULT_OK) {
                Post post = (Post) data.getSerializableExtra(POST_ADDED);
                mPosts.add(post);
                //TODO: update databse
                setAdapterAndUpdateData();
            }
        }
    }
    private void setAdapterAndUpdateData() {
        // create a new mAdapter with the updated mComments array
        // this will "refresh" our recycler view
        mAdapter = new PostAdapter(getActivity(), mPosts);
        mRecyclerView.setAdapter(mAdapter);
    }

}
