package com.example.comp2000assessment.bookings;

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
import java.text.ParseException;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comp2000assessment.databases.BookingsDatabaseHelper;
import com.example.comp2000assessment.homepages.GuestHomepage;
import com.example.comp2000assessment.R;
import com.example.comp2000assessment.notifications.NotificationsHelper;
import com.example.comp2000assessment.users.AppUser;
import com.example.comp2000assessment.users.Login_Activity;
import com.example.comp2000assessment.users.ManageUser;

import java.util.Date;
import java.text.SimpleDateFormat;
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

        //get the current user using ManageUser
        AppUser currentUser = ManageUser.getInstance().getCurrentUser();

        //check that current user isnt null
        if (currentUser == null) {
            //in case the app was killed in the background, send user back to the login screen
            //as a safety measure
            Intent intent = new Intent(this, Login_Activity.class);
            startActivity(intent);
            finish();
            return;
        }
        //get current user's name
        String user_firstname = currentUser.getFirstname();
        String user_lastname = currentUser.getLastname();

        //getting elements by their ids
        EditText firstNameInput = findViewById(R.id.br_firstNameInput);
        EditText lastNameInput = findViewById(R.id.br_lastNameInput);
        Spinner noGuestsSpinner = findViewById(R.id.guestNoDropdown);
        EditText specialNotesBox = findViewById(R.id.specialNotesInput);

        //setting text to the input fields
        firstNameInput.setText(user_firstname);
        lastNameInput.setText(user_lastname);

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
                //retrieving values from the fields
                String firstName = firstNameInput.getText().toString().trim();
                String lastName = lastNameInput.getText().toString().trim();
                int noGuests = Integer.parseInt(noGuestsSpinner.getSelectedItem().toString());
                String specialNotes = specialNotesBox.getText().toString();
                String date = dateText.getText().toString();
                String time = timeText.getText().toString();

                //adding check to reduce risk of guest name not matching their account name
                //and hence booking not being found
                if(!firstName.equals(user_firstname) || !lastName.equals(user_lastname)){
                    Intent intent = new Intent(Reservation_Enquiry.this, Enquiry_Failed_Activity.class);
                    intent.putExtra("errorMsg", "Your name does not match the name on your account!");
                    startActivity(intent);
                    return;
                }

                //handling date and time being empty
                if(date.equals("__/__/__") || time.equals("__:__")){
                    Intent intent = new Intent(Reservation_Enquiry.this, Enquiry_Failed_Activity.class);
                    intent.putExtra("errorMsg", "Please select a date and time!");
                    startActivity(intent);
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                try {
                    Date selectedDate = sdf.parse(date);

                    //getting today's date to compare
                    Calendar todayCal = Calendar.getInstance();
                    todayCal.set(Calendar.HOUR_OF_DAY, 0);
                    todayCal.set(Calendar.MINUTE, 0);
                    todayCal.set(Calendar.SECOND, 0);
                    todayCal.set(Calendar.MILLISECOND, 0);
                    Date today = todayCal.getTime();

                    //handling if entered date is before the local date
                    if (selectedDate != null && selectedDate.before(today)) {
                        Intent intent = new Intent(Reservation_Enquiry.this, Enquiry_Failed_Activity.class);
                        intent.putExtra("errorMsg", "You can't select a date before today's!");
                        startActivity(intent);
                        return;
                    }

                    // check that entered time is not between 10:00 and 21:00
                    String[] timeParts = time.split(":");
                    int hour = Integer.parseInt(timeParts[0]);
                    int minute = Integer.parseInt(timeParts[1]);

                    if (hour < 10 || hour > 21 || (hour == 21 && minute > 0)) {
                        Intent intent = new Intent(Reservation_Enquiry.this, Enquiry_Failed_Activity.class);
                        intent.putExtra("errorMsg", "The Restaurant takes bookings between 10:00-21:00!");
                        startActivity(intent);
                        return;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //creating an instance of booking item (unconfirmed request by guest constructor used here)
                BookingRecord newRequest = new BookingRecord(firstName, lastName, noGuests, date, time, 0, specialNotes, R.drawable.ic_people_group);

                //adding the new request in the Bookings DB
                BookingsDatabaseHelper bookingDb = new BookingsDatabaseHelper(Reservation_Enquiry.this);
                boolean addRequestResult = bookingDb.addBooking(newRequest);

                if(addRequestResult){
                    Intent intent = new Intent(Reservation_Enquiry.this, Enquiry_Sent_Activity.class);

                    //push notification
                    NotificationsHelper.displayNotification(Reservation_Enquiry.this, "Reservation Enquiry Sent", "You have enquired for: " + date + ", " + time);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Reservation_Enquiry.this, Enquiry_Failed_Activity.class);
                    startActivity(intent);
                }


            }
        });

        ImageButton resEnquiryToHomeBtn = findViewById(R.id.reservationHomeIcon);
        resEnquiryToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reservation_Enquiry.this, GuestHomepage.class);
                startActivity(intent);
            }
        });

    }
}