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
import com.inovex.bikroyik.UI.activity.DueListActivity;
import com.inovex.bikroyik.model.DueData;
import com.inovex.bikroyik.utils.ApiConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DueClientAdapter extends RecyclerView.Adapter<DueClientAdapter.MyViewHolder> {
    private static DueClientAdapter dueClientAdapter = null;
    private List<DueData> list = null;
    private Context mContext;

    public DueClientAdapter(Context mContext,List<DueData> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.child_due_client, parent, false);
        return new DueClientAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DueData data = list.get(position);

        if (!TextUtils.isEmpty(data.getClientName())){
            holder.tv_contactName.setText(data.getClientName());

            Integer[] colorsArray = {R.color.white_4, R.color.white_5};
            int number = position%2;



            TextDrawable drawable = TextDrawable.builder().beginConfig()
                    .width(40)  // width in px
                    .height(40) // height in px
                    .endConfig()
                    .buildRound(data.getClientName()
                            .trim().substring(0, 1), mContext.getResources().getColor(R.color.light_coral));

            holder.profileImage.setImageDrawable(drawable);

            if (data.getImage() != null){
                Picasso.get()
                        .load(ApiConstants.GET_CLIENT_IMAGE_BASE_URL+data.getImage())
                        .fit().centerCrop()
                        .placeholder(drawable)
                        .error(drawable)
                        .into(holder.profileImage);
            }


        }

        if (!TextUtils.isEmpty(data.getMobile())){
            holder.tv_contactNumber.setText(data.getMobile());
        }

        if (!TextUtils.isEmpty(data.getTotalDue())){
            String value = data.getTotalDue();
            Double totalDue = Double.valueOf(value);
            holder.tv_dueAmount.setText("BDT "+Math.round(totalDue));
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sendName = data.getClientName().toString();
                String mobileNumber = data.getMobile().toString();
                String id = data.getClientId().toString();

                Context context = v.getContext();
                Intent intent = new Intent(context, DueListActivity.class);
                intent.putExtra("sendName",sendName);
                intent.putExtra("sendNumber",mobileNumber);
                intent.putExtra("sendId",id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                /*FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("sendName",sendName);
                FragmentDue fragmentDue = new FragmentDue();
                fragmentDue.setArguments(bundle);
                fragmentTransaction.replace(R.id.dueLayoutId,fragmentDue).commit();
*/
            }
        });



    }


    @Override
    public int getItemCount() {
        /*if (list != null){
            return list.size();
        }
        return 0;*/
        return (list == null) ? 0 : list.size();
    }

    public void filterList(ArrayList<DueData> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    public void updateList(ArrayList<DueData> filteredList) {
        list = filteredList;
        notifyDataSetChanged();

    }

    protected class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImage;
        TextView tv_contactName;
        TextView tv_contactNumber, tv_dueAmount, tv_dueStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = (ImageView) itemView.findViewById(R.id.profile_image);
            tv_contactName = (TextView) itemView.findViewById(R.id.tv_contactName);
            tv_contactNumber = (TextView) itemView.findViewById(R.id.tv_phoneNumber);
            tv_dueAmount = (TextView) itemView.findViewById(R.id.tv_dueAmount);
            tv_dueStatus = (TextView) itemView.findViewById(R.id.tv_dueStatus);
        }
    }


}
