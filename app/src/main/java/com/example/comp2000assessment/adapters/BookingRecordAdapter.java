package com.example.comp2000assessment.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000assessment.bookings.BookingRecord;
import com.example.comp2000assessment.bookings.DeleteBooking_Activity;
import com.example.comp2000assessment.bookings.Guest_EditBooking_Activity;
import com.example.comp2000assessment.R;

import java.util.List;


public class BookingRecordAdapter extends RecyclerView.Adapter<BookingRecordAdapter.BookingViewHolder> {

    public List<BookingRecord> bookingList;
    private Context context;

    private String user_firstname, user_lastname, user_contact, user_email, user_username, user_password, user_usertype;
    private boolean user_logged_in;

    public BookingRecordAdapter(Context context, List<BookingRecord> bookingList, String user_firstname, String user_lastname, String contact, String email,
                                String username, String password, String usertype, boolean user_logged_in) {
        this.context = context;
        this.bookingList = bookingList;

        this.user_firstname = user_firstname;
        this.user_lastname = user_lastname;
        this.user_contact = contact;
        this.user_email = email;
        this.user_username = username;
        this.user_password = password;
        this.user_usertype = usertype;
        this.user_logged_in = user_logged_in;
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView time;
        ImageView peopleGroupIcon;
        TextView noOfGuests;
        ImageButton editButton;
        ImageButton deleteButton;
        TextView specialRequestsInfo;


        public BookingViewHolder(View bookingRecordView) {
            super(bookingRecordView);
            date = bookingRecordView.findViewById(R.id.booking_date);
            time = bookingRecordView.findViewById(R.id.booking_time);
            peopleGroupIcon = bookingRecordView.findViewById(R.id.group_icon);
            noOfGuests = bookingRecordView.findViewById(R.id.noGuests);
            editButton = bookingRecordView.findViewById(R.id.editBtn);
            deleteButton = bookingRecordView.findViewById(R.id.deleteBtn);
            specialRequestsInfo = bookingRecordView.findViewById(R.id.specialReqsText);
        }

    }

    @Override
    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_record, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookingViewHolder holder, int position) {
        BookingRecord item = bookingList.get(position);
        holder.date.setText(item.date);
        holder.time.setText(item.time);
        holder.noOfGuests.setText(String.format("%d", item.numberOfGuests));
        holder.peopleGroupIcon.setImageResource(item.peopleGroupIconId);
        holder.specialRequestsInfo.setText(item.specialRequest);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Guest_EditBooking_Activity.class);

                //passing the booking
                intent.putExtra("bookingID", item.getBookingID());
                intent.putExtra("guestFirstName", item.guestFirstName);
                intent.putExtra("guestLastName", item.guestLastName);
                intent.putExtra("date", item.date);
                intent.putExtra("time", item.time);
                intent.putExtra("numberOfGuests", item.numberOfGuests);
                intent.putExtra("confirmed", item.confirmed);
                intent.putExtra("specialRequest", item.specialRequest);
                intent.putExtra("tableNo", item.tableNo);

                //passing details
                intent.putExtra("user_firstname", user_firstname);
                intent.putExtra("user_lastname", user_lastname);
                intent.putExtra("user_contact", user_contact);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_username", user_username);
                intent.putExtra("user_password", user_password);
                intent.putExtra("user_usertype", user_usertype);
                intent.putExtra("user_logged_in", user_logged_in);

                context.startActivity(intent);

            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeleteBooking_Activity.class);

                //passing bookingID
                intent.putExtra("bookingID", item.getBookingID());

                //passing the name of screen to go back to
                intent.putExtra("screenName", "MyBookings");

                //passing details
                intent.putExtra("user_firstname", user_firstname);
                intent.putExtra("user_lastname", user_lastname);
                intent.putExtra("user_contact", user_contact);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_username", user_username);
                intent.putExtra("user_password", user_password);
                intent.putExtra("user_usertype", user_usertype);
                intent.putExtra("user_logged_in", user_logged_in);

                context.startActivity(intent);

            }
        });


    }

    public int getItemCount() {
        if (bookingList == null) {
            return 0;
        }return bookingList.size();
    }

}


