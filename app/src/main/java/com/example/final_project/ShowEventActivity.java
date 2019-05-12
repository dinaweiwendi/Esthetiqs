package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.final_project.Model.Event;

import java.util.Date;

public class ShowEventActivity extends AppCompatActivity {

    private static final String TAG = EventsFeedFragment.class.getSimpleName();

    private Event mEvent;

    public CardView mEventBriefCardLayout;
    public CardView mEventDescCardLayout;
    public TextView mEventNameText;
    public TextView mEventTimeText;
    public TextView mEventLocationText;
    public TextView mEventPaymentText;
    public TextView mEventGenreText;
    public TextView mEventDescriptionText;
    public ImageView mEventImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        mEvent = (Event) getIntent().getSerializableExtra("Event");

        mEventBriefCardLayout = findViewById(R.id.event_brief_card_layout);
        mEventDescCardLayout = findViewById(R.id.event_description_card_layout);
        mEventNameText = mEventBriefCardLayout.findViewById(R.id.text_event_name);
        mEventTimeText = mEventBriefCardLayout.findViewById(R.id.text_event_time);
        mEventLocationText = mEventBriefCardLayout.findViewById(R.id.text_event_location);
        mEventPaymentText = mEventBriefCardLayout.findViewById(R.id.text_event_payment);
        mEventGenreText = mEventBriefCardLayout.findViewById(R.id.text_event_genre);
        mEventDescriptionText = mEventDescCardLayout.findViewById(R.id.text_event_description);
        mEventImage = mEventBriefCardLayout.findViewById(R.id.image_event);

        mEventNameText.setText(mEvent.name);
        mEventTimeText.setText(new Date(mEvent.time).toString());
        mEventLocationText.setText(mEvent.location);
        mEventPaymentText.setText(mEvent.payment);
        mEventGenreText.setText(mEvent.genre);
        mEventDescriptionText.setText(mEvent.description);
        if (mEvent.imagePath != null) {
            mEventImage.setVisibility(View.VISIBLE);
            Glide.with(this /* context */)
                    .load(mEvent.imagePath)
                    .into(mEventImage);
        }
    }
}

