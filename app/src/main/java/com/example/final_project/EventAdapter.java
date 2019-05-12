package com.example.final_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.final_project.Model.Event;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sifang on 4/23/19.
 */

public class EventAdapter extends Adapter implements Filterable {

    private static final String TAG = EventsFeedFragment.class.getSimpleName();

    private Context mContext;
    private ArrayList<Event> mEvents;
    private ArrayList<Event> mFilteredEvents;

    public EventAdapter(Context context, ArrayList<Event> events) {
        mContext = context;
        mEvents = events;
        mFilteredEvents = events;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.event_card_layout, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Event event = mFilteredEvents.get(position);
        ((EventViewHolder) holder).bind(mContext,event);
    }

    @Override
    public int getItemCount() {
        return mFilteredEvents.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                Log.i(TAG, charString);
                if (charString.isEmpty()) {
                    mFilteredEvents = mEvents;
                } else {
                    ArrayList<Event> filteredList = new ArrayList<>();
                    for (Event event : mEvents) {
                        if (event.name.toLowerCase().contains(charString.toLowerCase())
                                || event.location.toLowerCase().contains(charString.toLowerCase())
                                || event.genre.toLowerCase().contains(charString.toLowerCase())
                                || event.description.toLowerCase().contains(charString.toLowerCase())) {
                            Log.i(TAG, event.toString() + " Added");
                            filteredList.add(event);
                        }
                    }

                    mFilteredEvents = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredEvents;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredEvents = (ArrayList<Event>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Event mEvent;

    // each data item is just a string in this case
    public CardView mEventCardLayout;
    public TextView mEventNameText;
    public TextView mEventTimeText;
    public TextView mEventLocationText;
    public TextView mEventPaymentText;
    public TextView mEventGenreText;
    public ImageView mEventImage;

    public EventViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mEventCardLayout = itemView.findViewById(R.id.event_card_layout);
        mEventNameText = mEventCardLayout.findViewById(R.id.text_event_name);
        mEventTimeText = mEventCardLayout.findViewById(R.id.text_event_time);
        mEventLocationText = mEventCardLayout.findViewById(R.id.text_event_location);
        mEventPaymentText = mEventCardLayout.findViewById(R.id.text_event_payment);
        mEventGenreText = mEventCardLayout.findViewById(R.id.text_event_genre);
        mEventImage = mEventCardLayout.findViewById(R.id.image_event_brief);
    }

    void bind(Context context, Event event) {
        mEvent = event;
        mEventNameText.setText(event.name);
        mEventTimeText.setText(new Date(event.time).toString());
        mEventLocationText.setText(event.location);
        mEventPaymentText.setText(event.payment);
        mEventGenreText.setText(event.genre);
        if (event.genre.equals("Hip Hop")) {
            mEventImage.setImageDrawable(context.getDrawable(R.drawable.hiphop));
        } else if (event.genre.equals("Ballet")){
            mEventImage.setImageDrawable(context.getDrawable(R.drawable.ballet));
        } else if (event.genre.equals("Salsa")){
            mEventImage.setImageDrawable(context.getDrawable(R.drawable.salsa));
        }
    }

    @Override
    public void onClick(View v) {
        Intent showEvent = new Intent(v.getContext(), ShowEventActivity.class);
        showEvent.putExtra("Event", mEvent);
        ((Activity) v.getContext()).startActivity(showEvent);
    }



}


