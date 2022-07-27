package com.inovex.bikroyik.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.AppUtil;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.activity.NotificationActivity;
import com.inovex.bikroyik.model.NoticeModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;
import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    ArrayList<NoticeModel> noticeModelArrayList;
    Context mContext;
    boolean downloaded = false;

    public MessageAdapter(ArrayList<NoticeModel> noticeModelArrayList, Context context) {
        this.noticeModelArrayList = noticeModelArrayList;
        mContext = context;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.message_list_item, parent, false);
        MessageViewHolder viewHolder = new MessageViewHolder(listItem);
        return viewHolder;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        final NoticeModel noticeModel = noticeModelArrayList.get(position);

        Timestamp timestamp = new Timestamp(Long.parseLong(noticeModel.getNotice_id()));

        holder.date.setText(Constants.notificationDateTimeFormate(timestamp));
        holder.title.setText(noticeModel.getNotice_title().trim());
        String image_url = noticeModel.getFileUrl();
        String img_type = noticeModel.getFileType();

        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            holder.tvMessageDetails.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        holder.tvMessageDetails.setText(noticeModel.getNotice_description());
        if(!TextUtils.isEmpty(image_url) && !TextUtils.isEmpty(img_type) && !image_url.equalsIgnoreCase("null")
                && !img_type.equalsIgnoreCase("null")&& image_url.length()>0 && img_type.length() > 0){
            holder.imageView.setVisibility(View.VISIBLE);
//            RequestCreator cursor = Picasso.get().load(message.getImageUrl());
//            cursor.into(holder.imageView);
////            .into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,30,0);
            holder.date.setLayoutParams(params);
            // holder.imageView.setVisibility(View.INVISIBLE);
        }

        Random random = new Random();
        int max = 10, min = 0;
        int randNumber = random.nextInt(max - min + 1);

        Integer[] colorsArray = {R.color.colorPrimary, R.color.dark_pink, R.color.deep_pink, R.color.blue_violet, R.color.light_coral,
                R.color.violet, R.color.magenta, R.color.light_see_green, R.color.light_blue, R.color.sky_blue, R.color.colorAccent};

        TextDrawable drawable = TextDrawable.builder().beginConfig()
                .width(40)  // width in px
                .height(40) // height in px
                .endConfig()
                .buildRound(noticeModel.getNotice_title().trim().substring(0, 1), mContext.getResources().getColor(colorsArray[randNumber]));

        holder.iv_firstLetter.setImageDrawable(drawable);
        if(!TextUtils.isEmpty(image_url) && !image_url.equalsIgnoreCase("null")) {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (img_type.equalsIgnoreCase("pdf") || img_type.equalsIgnoreCase("mp3")
                    ||img_type.equalsIgnoreCase("mp4")){
                        showFileDialog(mContext, noticeModel.getFileUrl());

                    } else {
                        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();

                        int DeviceTotalWidth = metrics.widthPixels;
                        int DeviceTotalHeight = metrics.heightPixels;
                        Dialog dialog = new Dialog(mContext);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setLayout(DeviceTotalWidth, DeviceTotalHeight);
                        dialog.setContentView(R.layout.message_image_dialog);
                        ImageView imageView = dialog.findViewById(R.id.ivMessage);
//                          Glide.with(mContext).load(message.getImageUrl())
//                          .into(imageView);
                        Log.v("_mdm_", noticeModel.getFileUrl());
                        Picasso.get().load(noticeModel.getFileUrl()).into(imageView);
                        dialog.setCancelable(true);

                        dialog.show();
                    }
                }
            });
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeModel noticeModel_serializable = new NoticeModel();
                noticeModel_serializable.setNotice_title(noticeModel.getNotice_title());
                noticeModel_serializable.setNotice_description(noticeModel.getNotice_description());


                Timestamp timestamp = new Timestamp(Long.parseLong(noticeModel.getNotice_id()));
                noticeModel_serializable.setDateTime(Constants.notificationDateTimeFormate(timestamp));
                noticeModel_serializable.setNotice_id(noticeModel.getNotice_id());
                noticeModel_serializable.setFileUrl("");

                if(!TextUtils.isEmpty(image_url) && image_url.equalsIgnoreCase("null")){
                    noticeModel_serializable.setFileUrl(noticeModel.getFileUrl());
                }

                Intent intent = new Intent(mContext, NotificationActivity.class);
                intent.putExtra("notification", noticeModel_serializable);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
       return noticeModelArrayList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView date;
        TextView title;
        TextView priority;
        TextView tvMessageDetails;
        LinearLayout llAttachment;
        ImageView iv_firstLetter;

        MessageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivFile);
            date = (TextView) itemView.findViewById(R.id.tvDate);
            title = (TextView) itemView.findViewById(R.id.tvMessageTitle);
//            priority = (TextView) itemView.findViewById(R.id.tvPriority);
            iv_firstLetter = (ImageView) itemView.findViewById(R.id.iv_firstLetterIcon);
            tvMessageDetails = itemView.findViewById(R.id.tvMessageDetails);
            llAttachment = itemView.findViewById(R.id.llAttachment);


        }
    }


    public void showFileDialog(Context context, String fileUrl) {

        AppDatabaseHelper databaseHelper = new AppDatabaseHelper(context);
        SessionManager sessionManager = new SessionManager(context);
        NoticeModel notificationFile = databaseHelper.getSingleFileData(fileUrl);
        if (notificationFile.getNotice_id() != null){
            downloaded = false;
            long noticeId = Long.parseLong(notificationFile.getNotice_id());
            Timestamp ts=new Timestamp(noticeId);
            Date date = ts;
            final String fileName = Constants.getFileNameByTimeStamp(ts);
            String fileDate = Constants.notificationDateTimeFormate(ts);
            String fileSize = notificationFile.getFileSize();
            String details = notificationFile.getNotice_description();
            final String fileType = notificationFile.getFileType();

            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.file_details_dialog);

            TextView tvDialogFileName = dialog.findViewById(R.id.tvDialogFileName);
            TextView tvDialogFileDate = dialog.findViewById(R.id.tvDialogFileDate);
            TextView tvDialogFileSize = dialog.findViewById(R.id.tvDialogFileSize);
            TextView tvDialogFileDetails = dialog.findViewById(R.id.tvDialogFileDetails);
            TextView tvFileOpenOrDownload = dialog.findViewById(R.id.tvFileOpenOrDownload);
            TextView tvDialogFileCancel = dialog.findViewById(R.id.tvDialogFileCancel);

            tvDialogFileName.setText(fileName);
            tvDialogFileDate.setText("Date: " + fileDate);
            tvDialogFileSize.setText("Size: " + fileSize);
            tvDialogFileDetails.setText(details);
            tvFileOpenOrDownload.setText("DOWNLOAD");
            tvDialogFileCancel.setText("CANCEL");
            String downloadedFile = fileName + "." + fileType;


            File appSpecificExternalDir = new File(mContext.getExternalFilesDir(AppUtil.getDirName(fileType)), downloadedFile);
            if (appSpecificExternalDir.exists()) {
                Log.v("_mdm_", appSpecificExternalDir.getAbsolutePath());
                downloaded = true;
                tvFileOpenOrDownload.setText("OPEN");
            } else {
                Log.v("_mdm_", "null file");
                downloaded = false;
            }

            tvFileOpenOrDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                File file = AppUtil.getFileStorageDir(mContext, fileType);
                    if (!downloaded) {
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
                        request.setDescription(fileName);
                        request.setTitle("MDM FILE DOWNDLOAD");
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalFilesDir(mContext, AppUtil.getDirName(fileType), downloadedFile);
                        DownloadManager manager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                        manager.enqueue(request);
                        dialog.dismiss();
                    } else {
                        File appSpecificExternalDir = new File(mContext.getExternalFilesDir(AppUtil.getDirName(fileType)), downloadedFile);
//                    if (appSpecificExternalDir != null) {
//                        Log.v("_tst_", appSpecificExternalDir.getAbsolutePath());
//
//                    } else {
//                        Log.v("_tst_", "null file");
//                    }
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", appSpecificExternalDir), AppUtil.getIntentViewType(fileType));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        try {
                            dialog.dismiss();
                            mContext.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(mContext, "No file Viewer Installed", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });

            tvDialogFileCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }

}
