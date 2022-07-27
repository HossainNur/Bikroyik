package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;

import java.util.List;

/**
 * Created by DELL on 11/8/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.contactsViewHolder> {


    private List<Contacts> contactsList;
    Context nContext;

    public ContactAdapter(Context mcontext, List<Contacts> contactsList) {
        this.contactsList = contactsList;
        nContext = mcontext;
    }

    @Override
    public contactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contacts_list_row, parent, false);

        return new contactsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(contactsViewHolder holder, int position) {
        final Contacts contacts = contactsList.get(position);

        holder.contactsTitle.setText(contacts.getContactsTitle());
        holder.contactsNumber.setText(contacts.getContactsNumber());
        holder.contactsType.setText(contacts.getContactsType());
        holder.contactsAddress.setText(contacts.getContactsAddress());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:" + contacts.getContactsNumber()));
//                nContext.startActivity(intent);
//            }
//        });

        holder.llCallContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + contacts.getContactsNumber()));
                nContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public class contactsViewHolder extends RecyclerView.ViewHolder {
        public TextView contactsTitle, contactsNumber, contactsAddress, contactsType;
        public LinearLayout llCallContact;

        public contactsViewHolder(View view) {
            super(view);
            contactsTitle = (TextView) view.findViewById(R.id.tvContactsTitle);
            contactsNumber = (TextView) view.findViewById(R.id.tvContactsNumber);
            llCallContact = (LinearLayout) view.findViewById(R.id.llCallContact);
            contactsAddress = view.findViewById(R.id.tvContactsAddress);
            contactsType = view.findViewById(R.id.tvContactsType);
        }

    }
}
