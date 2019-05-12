package com.example.final_project;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.final_project.Model.Event;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = EventsFeedFragment.class.getSimpleName();
    private final int PICK_IMAGE_REQUEST = 71;

    private String mGenre;
    private long mTime;
    private Event mEvent;
    private Uri filePath;
    private String mImagePath;

    int mYear;
    int mMonth;
    int mDay;
    int mHour;
    int mMinute;
    String date_time = "";


    TextInputEditText mTitleInputBox;
    EditText mTimeInputBox;
    TextInputEditText mLocationInputBox;
    TextInputEditText mPaymentInputBox;
    Spinner mGenreSpinner;
    TextInputEditText mDescriptionInputBox;
    Button mConfirmButton;
    Button mUploadImageButton;
    ImageView mUploadedImage;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mTitleInputBox = (TextInputEditText) findViewById(R.id.text_event_title);
        mTimeInputBox = (EditText) findViewById(R.id.text_event_time);
        mLocationInputBox = (TextInputEditText) findViewById(R.id.text_event_location);
        mPaymentInputBox = (TextInputEditText) findViewById(R.id.text_event_payment);
        mGenreSpinner = (Spinner) findViewById(R.id.spinner_event_genre);
        mDescriptionInputBox = (TextInputEditText) findViewById(R.id.text_event_description);
        mConfirmButton = (Button) findViewById(R.id.button_confirm);
        mUploadImageButton = (Button) findViewById(R.id.button_upload_image);
        mUploadedImage = (ImageView) findViewById(R.id.image_upload_image);

        // Time picker
        TextInputLayout textInputCustomEndIcon = findViewById(R.id.event_time);
        textInputCustomEndIcon
                .setEndIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker();
                    }
                });

        // Genre Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genre_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenreSpinner.setAdapter(adapter);
        mGenreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGenre = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Confirm Button
        mConfirmButton.setOnClickListener(this);
        // Image Upload Button
        mUploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

    }

    private void datePicker(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        timePicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void timePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        try {
                            Date date = format.parse(date_time+" "+hourOfDay + ":" + minute+ ":" + "00");
                            mTime = date.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        mTimeInputBox.setText(date_time+" "+hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    public void onClick(View v) {
        uploadImage();
        String title = mTitleInputBox.getText().toString();
        String location = mLocationInputBox.getText().toString();
        String payment = mPaymentInputBox.getText().toString();
        String description = mDescriptionInputBox.getText().toString();
        if (TextUtils.isEmpty(title)){
            Toast.makeText(this, "Title cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mTime == 0) {
            Toast.makeText(this, "Date and time is not valid! Please select again.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(location)){
            Toast.makeText(this, "Location cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(payment)){
            Toast.makeText(this, "Payment information cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mGenre == null) {
            Toast.makeText(this, "Genre cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(description)){
            Toast.makeText(this, "Description cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mImagePath != null) {
            mEvent = new Event(title, mTime, location, payment, mGenre, description, mImagePath);
        } else {
            mEvent = new Event(title, mTime, location, payment, mGenre, description);
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra(EventsFeedFragment.EVENT_ADDED, mEvent);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null ) {
            mUploadedImage.setVisibility(View.VISIBLE);
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                mUploadedImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            UploadTask uploadTask = ref.putFile(filePath);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(AddEventActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddEventActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        mImagePath = downloadUri.toString();
                        Log.i(TAG, "Image path is: " + mImagePath);
                    } else {
                        Toast.makeText(AddEventActivity.this, "Failed to get image reference.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}