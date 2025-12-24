package com.example.comp2000assessment.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000assessment.bookings.BookingRecord;
import com.example.comp2000assessment.bookings.Change_Status;
import com.example.comp2000assessment.R;

import java.util.List;


public class S_BookingRequestAdapter extends RecyclerView.Adapter<S_BookingRequestAdapter.BookingReqViewHolder> {

    public List<BookingRecord> bookingList;
    private Context context;
    private String staff_firstname, staff_lastname, staff_contact, staff_email, staff_username, staff_password, staff_usertype;
    private boolean staff_logged_in;

    public S_BookingRequestAdapter(Context context, List<BookingRecord> bookingList, String staff_firstname, String staff_lastname, String contact, String email,
                                   String username, String password, String usertype, boolean staff_logged_in) {
        this.context = context;
        this.bookingList = bookingList;
        this.staff_firstname = staff_firstname;
        this.staff_lastname = staff_lastname;
        this.staff_contact = staff_contact;
        this.staff_email = staff_email;
        this.staff_username = staff_username;
        this.staff_password = staff_password;
        this.staff_usertype = staff_usertype;
        this.staff_logged_in = staff_logged_in;
    }

    public static class BookingReqViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView time;
        ImageView peopleGroupIcon;
        TextView noOfGuests;
        Button changeStatusBtn;
        TextView guestFullName;
        TextView specialRequestsInfo;


        public BookingReqViewHolder(View bookingRecordView) {
            super(bookingRecordView);
            date = bookingRecordView.findViewById(R.id.u_booking_date);
            time = bookingRecordView.findViewById(R.id.u_booking_time);
            peopleGroupIcon = bookingRecordView.findViewById(R.id.u_group_icon);
            noOfGuests = bookingRecordView.findViewById(R.id.u_noGuests);
            guestFullName = bookingRecordView.findViewById(R.id.u_guest_name);
            changeStatusBtn = bookingRecordView.findViewById(R.id.changeBookingStatusBtn);
            specialRequestsInfo = bookingRecordView.findViewById(R.id.u_specialReqsText);
        }

    }

    @Override
    public BookingReqViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_request_staff, parent, false);
        return new BookingReqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookingReqViewHolder holder, int position) {
        BookingRecord item = bookingList.get(position);
        holder.date.setText(item.date);
        holder.time.setText(item.time);

        //forming a string to hold and display the full name of the guest
        String fullName = item.guestFirstName + " " + item.guestLastName;
        holder.guestFullName.setText(fullName);

        holder.noOfGuests.setText(String.format("%d", item.numberOfGuests));
        holder.peopleGroupIcon.setImageResource(item.peopleGroupIconId);
        holder.specialRequestsInfo.setText(item.specialRequest);

        holder.changeStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Change_Status.class);
                intent.putExtra("bookingID", item.getBookingID());
                intent.putExtra("firstName", item.guestFirstName);
                intent.putExtra("lastName", item.guestLastName);
                intent.putExtra("specialRequest", item.specialRequest);
                intent.putExtra("fullName", fullName);
                intent.putExtra("date", item.date);
                intent.putExtra("time", item.time);
                intent.putExtra("noGuests", item.numberOfGuests);
                context.startActivity(intent);

                //passing the staff details
                intent.putExtra("staff_firstname", staff_firstname);
                intent.putExtra("staff_lastname", staff_lastname);
                intent.putExtra("staff_contact", staff_contact);
                intent.putExtra("staff_email", staff_email);
                intent.putExtra("staff_username", staff_username);
                intent.putExtra("staff_password", staff_password);
                intent.putExtra("staff_usertype", staff_usertype);
                intent.putExtra("staff_logged_in", staff_logged_in);

            }
        });


    }

    public int getItemCount() {
        if (bookingList == null) {
            return 0;
        }return bookingList.size();
    }

}
