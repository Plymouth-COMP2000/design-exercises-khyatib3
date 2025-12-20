package com.example.comp2000assessment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;

public class Reservation_Enquiry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservation_enquiry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.reservation_EnquiryScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton resEnq_HomeIcon = findViewById(R.id.reservationHomeIcon);
        //setting on click functionality
        resEnq_HomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Reservation_Enquiry.this, GuestHomepage.class);
                startActivity(intent);
            }
        });

        ImageButton calendarBtn = findViewById(R.id.calendarBtn);
        TextView dateText = findViewById(R.id.dateDisplay);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting current date to show that when the calendar opens
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //creating a date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(Reservation_Enquiry.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //changing text of date display to show the date selected by guest
                                String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
                                dateText.setText(selectedDate);
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });

        ImageButton timeBtn = findViewById(R.id.timeBtn);
        TextView timeText = findViewById(R.id.timeDisplay);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //like date, getting current local time to show that when time picker opens
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                //creating the time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Reservation_Enquiry.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                                timeText.setText(formattedTime);
                            }
                        }, hour, minute, true);

                timePickerDialog.show();
            }
        });


        Button sendRequestBtn = findViewById(R.id.submitRequestButton);
        //setting on click functionality
        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting the other user input fields
                EditText firstNameInput = findViewById(R.id.br_firstNameInput);
                EditText lastNameInput = findViewById(R.id.br_lastNameInput);
                Spinner noGuestsSpinner = findViewById(R.id.guestNoDropdown);
                EditText specialNotesBox = findViewById(R.id.specialNotesInput);

                //retrieving values from the fields
                String firstName = firstNameInput.getText().toString();
                String lastName = lastNameInput.getText().toString();
                int noGuests = Integer.parseInt(noGuestsSpinner.getSelectedItem().toString());
                String specialNotes = specialNotesBox.getText().toString();
                String date = dateText.getText().toString();
                String time = timeText.getText().toString();


                //creating an instance of booking item (unconfirmed request by guest constructor used here)
                BookingRecord newRequest = new BookingRecord(firstName, lastName, noGuests, date, time, 0, specialNotes, R.drawable.ic_people_group);

                //adding the new request in the Bookings DB
                BookingsDatabaseHelper bookingDb = new BookingsDatabaseHelper(Reservation_Enquiry.this);
                boolean addRequestResult = bookingDb.addBooking(newRequest);

                if(addRequestResult){
                    Intent intent = new Intent(Reservation_Enquiry.this, Enquiry_Sent_Activity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Reservation_Enquiry.this, Enquiry_Failed_Activity.class);
                    startActivity(intent);
                }


            }
        });

    }
}