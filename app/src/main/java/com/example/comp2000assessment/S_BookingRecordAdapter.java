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

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class S_BookingRecordAdapter extends RecyclerView.Adapter<S_BookingRecordAdapter.BookingRecordViewHolder> {

    public List<BookingRecord> bookingList;
    private Context context;

    public S_BookingRecordAdapter(Context context, List<BookingRecord> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
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




    }

    public int getItemCount() {
        if (bookingList == null) {
            return 0;
        }return bookingList.size();
    }

}
