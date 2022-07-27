package com.inovex.bikroyik.UI.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.activity.ContactListActivity;
import com.inovex.bikroyik.UI.activity.MakePaymentActivity;
import com.inovex.bikroyik.UI.activity.PaymentActivity;
import com.inovex.bikroyik.data.model.ClientListModel;
import com.inovex.bikroyik.interfaces.ClientCallback;
import com.inovex.bikroyik.utils.ApiConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import static android.Manifest.permission.CALL_PHONE;
public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {
    private static ClientAdapter clientAdapter = null;
    private List<ClientListModel> clientList;
    private Context mContext,context;
    private ClientCallback clientCallback;

    public static ClientAdapter getInstance(){
        if (clientAdapter == null){
            clientAdapter = new ClientAdapter();
            return clientAdapter;
        }
        return clientAdapter;
    }

    public void init(Context context, List<ClientListModel> clientList, ClientCallback clientCallback){
        this.mContext = context;
        this.clientList = clientList;
        this.clientCallback = clientCallback;
    }

    public void init2(Context context, List<ClientListModel> clientList, ClientCallback clientCallback){
        this.mContext = context;
        this.clientList = clientList;
        this.clientCallback = clientCallback;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.phone_list_child_view, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {

        ClientListModel client = clientList.get(position);

        if (!TextUtils.isEmpty(client.getName())){
            holder.tv_contactName.setText(client.getName());

            Integer[] colorsArray = {R.color.white_4, R.color.white_5};
            int number = position%2;



            TextDrawable drawable = TextDrawable.builder().beginConfig()
                    .width(40)  // width in px
                    .height(40) // height in px
                    .endConfig()
                    .buildRound(client.getName()
                            .trim().substring(0, 1), mContext.getResources().getColor(R.color.light_coral));



            if (client.getImage() != null){
                Picasso.get()
                        .load(ApiConstants.GET_CLIENT_IMAGE_BASE_URL+client.getImage())
                        .fit().centerCrop()
                        .placeholder(drawable)
                        .error(drawable)
                        .into(holder.profileImage);
            }

            holder.profileImage.setImageDrawable(drawable);

            /*sendData = client.getName().toString();*/


        }

        if (!TextUtils.isEmpty(client.getMobile())){
            holder.tv_contactNumber.setText(client.getMobile());
        }

        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = client.getMobile().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });

        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("smsto:"+client.getMobile());
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "Assalamualikom " + "Mr. "+ client.getName());
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(it);
            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d("clien_list###",""+client.getId());
               clientCallback.callback(client);

               // Toast.makeText(mContext,client.getName(),Toast.LENGTH_SHORT).show();

                //select_employe.setText(tv_contactName);
                /*String sendData = client.getName().toString();
                Context context = v.getContext();
                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra("sendkey",sendData);
                context.startActivity(intent);*/
                /*String phone_number= client.getMobile().toString();
                Intent phone_intent = new Intent(Intent.ACTION_CALL);
                phone_intent
                        .setData(Uri.parse("tel:"
                                + phone_number));

                // start Intent
                mContext.startActivity(phone_intent);

*/

            }
        });
    }


    @Override
    public int getItemCount() {
        if (clientList != null){
            return clientList.size();
        }
        return 0;
    }

    public void filterList(ArrayList<ClientListModel> filteredList) {
        clientList = filteredList;
        notifyDataSetChanged();
    }

    protected class ClientViewHolder extends RecyclerView.ViewHolder{
        public Object holder;
        //public String sendData;
        ImageView profileImage;
        TextView tv_contactName;
        TextView tv_contactNumber;
        ImageView phone,message;
        EditText select_employee;
        //String sendData;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = (ImageView) itemView.findViewById(R.id.profile_image);
            tv_contactName = (TextView) itemView.findViewById(R.id.tv_contactName);
            tv_contactNumber = (TextView) itemView.findViewById(R.id.tv_phoneNumber);
            phone = (ImageView) itemView.findViewById(R.id.callId);
            message = (ImageView) itemView.findViewById(R.id.messageId);
            //sendData = tv_contactNumber.getText().toString();
            //select_employee = (EditText) itemView.findViewById(R.id.selectEmployeeId);
        }
    }
}
