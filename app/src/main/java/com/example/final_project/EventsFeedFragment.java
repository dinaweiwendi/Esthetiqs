package com.example.final_project;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project.Model.Event;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class EventsFeedFragment extends Fragment {

    private static final String TAG = EventsFeedFragment.class.getSimpleName();
    public static final String EVENT_ADDED = "com.example.final_proj.added_event";
    static final int ADD_EVENT_REQUEST = 1;

    private ArrayList<Event> mEvents;

    private RecyclerView mRecyclerView;
    private EventAdapter mAdapter;
    private DatabaseReference mDatabase;
    private SearchView mSearchView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {

        View view = inflater.inflate(R.layout.fragment_events_feed, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mEvents = new ArrayList<>();
        loadEvents();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.events_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setHasOptionsMenu(true);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addEventIntent = new Intent(getContext(), AddEventActivity.class);
                startActivityForResult(addEventIntent, ADD_EVENT_REQUEST);
            }
        });

        return view;
    }


    private void setAdapterAndUpdateData() {
        // create a new mAdapter with the updated mComments array
        // this will "refresh" our recycler view
        mAdapter = new EventAdapter(getActivity(), mEvents);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadEvents() {
        mDatabase.child("Events").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
//                    Event tmp = singleSnapshot.getValue(Event.class);
                    mEvents.add(singleSnapshot.getValue(Event.class));
                }
                setAdapterAndUpdateData();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu,inflater);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        mSearchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_EVENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Event event = (Event) data.getSerializableExtra(EVENT_ADDED);
                mEvents.add(event);
                mDatabase.child("Events").child("Event").push().setValue(event);
                setAdapterAndUpdateData();
            }
        }
    }

}
