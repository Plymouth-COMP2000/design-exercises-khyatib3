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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comp2000assessment.databases.BookingsDatabaseHelper;
import com.example.comp2000assessment.R;

import java.util.Calendar;
import java.util.Locale;

public class Guest_EditBooking_Activity extends AppCompatActivity {
    Spinner g_editGuestNoSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity__guest_edit_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.guest_edit_booking_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //getting user details from account created
        String user_firstname = getIntent().getStringExtra("user_firstname");
        String user_lastname = getIntent().getStringExtra("user_lastname");
        String user_contact = getIntent().getStringExtra("user_contact");
        String user_email = getIntent().getStringExtra("user_email");
        String user_username = getIntent().getStringExtra("user_username");
        String user_password = getIntent().getStringExtra("user_password");
        String user_usertype = getIntent().getStringExtra("user_usertype");
        boolean user_logged_in = getIntent().getBooleanExtra("user_logged_in", true);

        //retrieving booking values passed
        String guestFirstName = getIntent().getStringExtra("guestFirstName");
        String guestLastName = getIntent().getStringExtra("guestLastName");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        int tableNo = getIntent().getIntExtra("tableNo", 0);
        String specialRequest = getIntent().getStringExtra("specialRequest");
        int numberOfGuests = getIntent().getIntExtra("numberOfGuests", 0);
        boolean confirmed = getIntent().getBooleanExtra("confirmed", false);
        int bookingID = getIntent().getIntExtra("bookingID", 0);

        //changing elements to pre fill booking details
        EditText fNameInput = findViewById(R.id.g_HolderFNameInput);
        fNameInput.setHint(guestFirstName);
        EditText lNameInput = findViewById(R.id.g_HolderLNameInput);
        lNameInput.setHint(guestLastName);

        TextView dateDisplay = findViewById(R.id.g_editDateDisplay);
        dateDisplay.setText(date);
        TextView timeDisplay = findViewById(R.id.g_editTimeDisplay);
        timeDisplay.setText(time);


        g_editGuestNoSpinner = findViewById(R.id.g_editGuestNoSpinner);
        //setting guest no spinner to show current number of guests option
        switch(numberOfGuests){
            case 1:
                g_editGuestNoSpinner.setSelection(1);
                break;
            case 2:
                g_editGuestNoSpinner.setSelection(2);
                break;
            case 3:
                g_editGuestNoSpinner.setSelection(3);
                break;
            case 4:
                g_editGuestNoSpinner.setSelection(4);
                break;
            case 5:
                g_editGuestNoSpinner.setSelection(5);
                break;
            case 6:
                g_editGuestNoSpinner.setSelection(6);
                break;
            case 7:
                g_editGuestNoSpinner.setSelection(7);
                break;
            case 8:
                g_editGuestNoSpinner.setSelection(8);
                break;
            case 9:
                g_editGuestNoSpinner.setSelection(9);
                break;
            case 10:
                g_editGuestNoSpinner.setSelection(10);
                break;
            default:
                g_editGuestNoSpinner.setSelection(0);
                break;
        }

        //on click listeners and validation for time pickers and date pickers
        ImageButton calendarBtn = findViewById(R.id.g_editDateBtn);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting current date to show that when the calendar opens
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //creating a date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(Guest_EditBooking_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //changing text of date display to show the date selected by guest
                                String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
                                dateDisplay.setText(selectedDate);
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });

        ImageButton timeBtn = findViewById(R.id.g_editTimeBtn);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //like date, getting current local time to show that when time picker opens
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                //creating the time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Guest_EditBooking_Activity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                                timeDisplay.setText(formattedTime);
                            }
                        }, hour, minute, true);

                timePickerDialog.show();
            }
        });

        //button cancel changes on click listener
        Button cancelBookingChangesBtn = findViewById(R.id.g_cancelBookingChangesBtn);
        cancelBookingChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_EditBooking_Activity.this, MyBookingsActivity.class);
                startActivity(intent);
            }
        });

        //button save changes on click listener
        Button updateBookingBtn = findViewById(R.id.g_updateBookingBtn);
        updateBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting new values
                String newFName = fNameInput.getText().toString();
                String newLName = lNameInput.getText().toString();
                String newDate = dateDisplay.getText().toString();
                String newTime = timeDisplay.getText().toString();
                int newGuestNo = Integer.parseInt(g_editGuestNoSpinner.getSelectedItem().toString());

                if(newFName.isEmpty() || newLName.isEmpty()){
                    Toast.makeText(Guest_EditBooking_Activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!newFName.equals(user_firstname) || !newLName.equals(user_lastname)){
                    Toast.makeText(Guest_EditBooking_Activity.this, "Your name does not match the name on your account!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //creating a booking record object to pass into updateBooking
                BookingRecord updatedBooking = new BookingRecord(
                        newFName,
                        newLName,
                        newGuestNo,
                        newDate,
                        newTime,
                        tableNo,
                        specialRequest,
                        R.drawable.ic_people_group
                );
                updatedBooking.setBookingID(bookingID);
                updatedBooking.confirmed = confirmed;
                updatedBooking.tableNo = tableNo;

                //getting instance of db
                BookingsDatabaseHelper db = new BookingsDatabaseHelper(Guest_EditBooking_Activity.this);
                boolean updateBookingResult = db.updateBooking(updatedBooking);

                if(updateBookingResult){
                    Intent intent = new Intent(Guest_EditBooking_Activity.this, MyBookingsActivity.class);

                    //passing the user details
                    intent.putExtra("user_firstname", user_firstname);
                    intent.putExtra("user_lastname", user_lastname);
                    intent.putExtra("user_contact", user_contact);
                    intent.putExtra("user_email", user_email);
                    intent.putExtra("user_username", user_username);
                    intent.putExtra("user_password", user_password);
                    intent.putExtra("user_usertype", user_usertype);
                    intent.putExtra("user_logged_in", user_logged_in);

                    Toast.makeText(Guest_EditBooking_Activity.this, "Booking updated", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    Toast.makeText(Guest_EditBooking_Activity.this, "Unable to update booking", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}