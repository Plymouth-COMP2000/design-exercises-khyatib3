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


    public BookingRecordAdapter(Context context, List<BookingRecord> bookingList) {
        this.context = context;
        this.bookingList = bookingList;

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


