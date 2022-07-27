package com.inovex.bikroyik.UI.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.activity.NewContact;
import com.inovex.bikroyik.data.model.PhoneBookModel;

import java.util.List;

public class PhoneBookAdapter extends RecyclerView.Adapter<PhoneBookAdapter.PhoneBookViewHolder> {

    private List<PhoneBookModel> phoneBookModelList;
    private Context mContext;


    public PhoneBookAdapter(Context context, List<PhoneBookModel> phoneBookModelList){
        this.mContext = context;
        this.phoneBookModelList = phoneBookModelList;
    }

    @NonNull
    @Override
    public PhoneBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.phone_list_child_view, parent, false);

        return new PhoneBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneBookViewHolder holder, int position) {
        PhoneBookModel phoneBookModel = phoneBookModelList.get(position);

        if (!TextUtils.isEmpty(phoneBookModel.getContactName())){
            holder.tv_contactName.setText(phoneBookModel.getContactName());

            Integer[] colorsArray = {R.color.white_4, R.color.white_5};
            int number = position%2;

            TextDrawable drawable = TextDrawable.builder().beginConfig()
                    .width(40)  // width in px
                    .height(40) // height in px
                    .endConfig()
                    .buildRound(phoneBookModel.getContactName()
                            .trim().substring(0, 1), mContext.getResources().getColor(R.color.light_coral));

            holder.profileImage.setImageDrawable(drawable);

        }

        if (!TextUtils.isEmpty(phoneBookModel.getPhoneNumber())){
            holder.tv_contactNumber.setText(phoneBookModel.getPhoneNumber());
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewContact.class);
                intent.putExtra("value", phoneBookModel);
                mContext.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        if (phoneBookModelList != null){
            return phoneBookModelList.size();
        }
        return 0;
    }

    protected class PhoneBookViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage;
        TextView tv_contactName;
        TextView tv_contactNumber;

        public PhoneBookViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = (ImageView) itemView.findViewById(R.id.profile_image);
            tv_contactName = (TextView) itemView.findViewById(R.id.tv_contactName);
            tv_contactNumber = (TextView) itemView.findViewById(R.id.tv_phoneNumber);
        }
    }
}
