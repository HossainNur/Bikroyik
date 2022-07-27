package com.inovex.bikroyik.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;

import java.util.List;

/**
 * Created by DELL on 8/5/2018.
 */


public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    private List<Attendance> attendanceList;

    public AttendanceAdapter(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }


    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_list_row, parent, false);

        return new AttendanceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AttendanceViewHolder holder, int position) {
        Attendance attendance = attendanceList.get(position);
        holder.attendanceDate.setText(attendance.getAttendanceDate());
        holder.attendanceIn.setText(attendance.getAttendanceIn());
        holder.attandanceOut.setText(attendance.getAttendanceOut());
        holder.tvAttendanceStatus.setText(attendance.getStatus());
//        if (position % 7 == 0 && position!=0) {
//            holder.llAttendanceRow.setBackgroundColor(Color.parseColor("#E43F3F"));
//            holder.attendanceDate.setTextColor(Color.parseColor("#FFFFFF"));
//            holder.attendanceIn.setTextColor(Color.parseColor("#FFFFFF"));
//            holder.attandanceOut.setTextColor(Color.parseColor("#FFFFFF"));
//        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {
        public TextView attendanceDate, attendanceIn, attandanceOut,tvAttendanceStatus;
        LinearLayout llAttendanceRow;

        public AttendanceViewHolder(View view) {
            super(view);
            attendanceDate = (TextView) view.findViewById(R.id.tvAttendanceDate);
            attendanceIn = (TextView) view.findViewById(R.id.tvAttendanceIn);
            attandanceOut = (TextView) view.findViewById(R.id.tvAttendanceOut);
            llAttendanceRow = (LinearLayout) view.findViewById(R.id.llAttendanceRow);
            tvAttendanceStatus=(TextView) view.findViewById(R.id.tvAttendanceStatus);
        }
    }
}

