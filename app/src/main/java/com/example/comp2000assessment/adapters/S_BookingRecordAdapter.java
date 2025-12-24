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
import com.example.comp2000assessment.R;
import com.example.comp2000assessment.bookings.DeleteBooking_Activity;
import com.example.comp2000assessment.bookings.Guest_EditBooking_Activity;
import com.example.comp2000assessment.bookings.Staff_EditBooking_Activity;

import java.util.List;


public class S_BookingRecordAdapter extends RecyclerView.Adapter<S_BookingRecordAdapter.BookingRecordViewHolder> {

    public List<BookingRecord> bookingList;
    private Context context;

    private String staff_firstname, staff_lastname, staff_contact, staff_email, staff_username, staff_password, staff_usertype;
    private boolean staff_logged_in;
    public S_BookingRecordAdapter(Context context, List<BookingRecord> bookingList, String staff_firstname, String staff_lastname, String contact, String email,
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

    public static class BookingRecordViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView time;
        ImageView peopleGroupIcon;
        TextView noOfGuests;
        ImageButton editBtn;
        ImageButton deleteBtn;
        TextView guestFullName;
        TextView specialRequestsInfo;


        public BookingRecordViewHolder(View bookingRecordView) {
            super(bookingRecordView);
            date = bookingRecordView.findViewById(R.id.s_booking_date);
            time = bookingRecordView.findViewById(R.id.s_booking_time);
            peopleGroupIcon = bookingRecordView.findViewById(R.id.s_group_icon);
            noOfGuests = bookingRecordView.findViewById(R.id.s_noGuests);
            guestFullName = bookingRecordView.findViewById(R.id.s_booking_holder_name);
            editBtn = bookingRecordView.findViewById(R.id.editBookingBtn);
            deleteBtn = bookingRecordView.findViewById(R.id.deleteBookingBtn);
            specialRequestsInfo = bookingRecordView.findViewById(R.id.s_specialReqsText);
        }

    }

    @Override
    public BookingRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.s_booking_record, parent, false);
        return new BookingRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookingRecordViewHolder holder, int position) {
        BookingRecord item = bookingList.get(position);
        holder.date.setText(item.date);
        holder.time.setText(item.time);

        //forming a string to hold and display the full name of the guest
        String fullName = item.guestFirstName + " " + item.guestLastName;
        holder.guestFullName.setText(fullName);

        holder.noOfGuests.setText(String.format("%d", item.numberOfGuests));
        holder.peopleGroupIcon.setImageResource(item.peopleGroupIconId);
        holder.specialRequestsInfo.setText(item.specialRequest);

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Staff_EditBooking_Activity.class);

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

                //passing the staff details
                intent.putExtra("staff_firstname", staff_firstname);
                intent.putExtra("staff_lastname", staff_lastname);
                intent.putExtra("staff_contact", staff_contact);
                intent.putExtra("staff_email", staff_email);
                intent.putExtra("staff_username", staff_username);
                intent.putExtra("staff_password", staff_password);
                intent.putExtra("staff_usertype", staff_usertype);
                intent.putExtra("staff_logged_in", staff_logged_in);

                context.startActivity(intent);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeleteBooking_Activity.class);

                //passing bookingID
                intent.putExtra("bookingID", item.getBookingID());

                //passing the staff details
                intent.putExtra("staff_firstname", staff_firstname);
                intent.putExtra("staff_lastname", staff_lastname);
                intent.putExtra("staff_contact", staff_contact);
                intent.putExtra("staff_email", staff_email);
                intent.putExtra("staff_username", staff_username);
                intent.putExtra("staff_password", staff_password);
                intent.putExtra("staff_usertype", staff_usertype);
                intent.putExtra("staff_logged_in", staff_logged_in);

                //passing name of screen to go back to
                intent.putExtra("screenName", "AllTables");
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
