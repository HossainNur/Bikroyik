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
import com.inovex.bikroyik.UI.activity.PaymentActivity;
import com.inovex.bikroyik.data.model.EmployeeListModel;
import com.inovex.bikroyik.utils.ApiConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeeListAdapater extends RecyclerView.Adapter<EmployeeListAdapater.MyViewHolder> {
    private List<EmployeeListModel> list;
    private Context mContext;



    public EmployeeListAdapater(Context mContext, List list) {
        /*this.mContext = applicationContext;
        this.list = arrayList;*/
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.employee_list, parent, false);
        return new EmployeeListAdapater.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        EmployeeListModel employeeListModel = list.get(position);

        if (!TextUtils.isEmpty(employeeListModel.getName())){
            holder.tv_contactName.setText(employeeListModel.getName());

            Integer[] colorsArray = {R.color.white_4, R.color.white_5};
            int number = position%2;



            TextDrawable drawable = TextDrawable.builder().beginConfig()
                    .width(40)  // width in px
                    .height(40) // height in px
                    .endConfig()
                    .buildRound(employeeListModel.getName()
                            .trim().substring(0, 1), mContext.getResources().getColor(R.color.light_coral));

            holder.profileImage.setImageDrawable(drawable);

           /* if (employeeListModel.getImage() != null){
                Picasso.get()
                        .load(ApiConstants.GET_CLIENT_IMAGE_BASE_URL+employeeListModel.getImage())
                        .fit().centerCrop()
                        .placeholder(drawable)
                        .error(drawable)
                        .into(holder.profileImage);
            }*/


        }

        if (!TextUtils.isEmpty(employeeListModel.getContact_number())){
            holder.tv_contactNumber.setText(employeeListModel.getContact_number());
        }

        /*if (!TextUtils.isEmpty(data.getTotalDue())){
            String value = data.getTotalDue();
            Double totalDue = Double.valueOf(value);
            holder.tv_dueAmount.setText("BDT "+Math.round(totalDue));
        }*/



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sendName = employeeListModel.getName().toString();
                String mobileNumber = employeeListModel.getContact_number().toString();
                Context context = v.getContext();
                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra("sendName",sendName);
                intent.putExtra("sendNumber",mobileNumber);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });



    }


    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public void filterList(List<EmployeeListModel> filteredList) {
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

        }
    }


}
