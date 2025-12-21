package com.example.comp2000assessment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class S_BookingRecordAdapter extends RecyclerView.Adapter<S_BookingRecordAdapter.BookingReqViewHolder> {

    public List<BookingRecord> bookingList;
    private Context context;

    public S_BookingRecordAdapter(Context context, List<BookingRecord> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
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

            }
        });


    }

    public int getItemCount() {
        if (bookingList == null) {
            return 0;
        }return bookingList.size();
    }

}
