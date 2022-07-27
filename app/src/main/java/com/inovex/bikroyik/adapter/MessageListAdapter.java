package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;

import java.util.List;

/**
 * Created by DELL on 11/14/2018.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageListViewHolder> {

    private List<MessageItem> messageItemList;
    Context mContext;

    public MessageListAdapter(List<MessageItem> messageItemList, Context nContext) {
        this.messageItemList = messageItemList;
        mContext = nContext;

    }

    @Override
    public MessageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list_row, parent, false);

        return new MessageListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageListViewHolder holder, int position) {


        MessageItem messageItem = messageItemList.get(position);
        String sender = messageItem.getMessageSender();
        String dateTime = messageItem.getMessageDate()+"  "+messageItem.getMessageTime();
        String title=messageItem.getMessageTitle();
        String message=messageItem.getMessage();

        holder.tvSenderName.setText(sender);
        holder.tvDateTime.setText(dateTime);
        holder.tvTitle.setText(title);
        holder.tvMessage.setText(message);
        Log.v("_sf",message);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return messageItemList.size();
    }

    public class MessageListViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSenderName, tvDateTime, tvTitle, tvMessage;

        public MessageListViewHolder(View view) {
            super(view);
            tvSenderName = (TextView) view.findViewById(R.id.tvSenderName);
            tvDateTime = (TextView) view.findViewById(R.id.tvMessageDateTime);
            tvTitle = (TextView) view.findViewById(R.id.tvMessageTitle);
            tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        }
    }
}
