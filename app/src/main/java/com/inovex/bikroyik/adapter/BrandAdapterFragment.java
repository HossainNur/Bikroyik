package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.activity.MainActivity;
import com.inovex.bikroyik.fragment.ProductFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BrandAdapterFragment extends BaseAdapter {

    private Context context;

    ArrayList<BrandClass> brandObjectList;

    public BrandAdapterFragment(Context context, ArrayList<BrandClass> brandObjectList) {
        this.context = context;
        this.brandObjectList = brandObjectList;
    }

    @Override
    public int getCount() {
        return brandObjectList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View v = convertView;
        // Inflate the layout for each list item
        LayoutInflater _inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (v == null) {
            v = _inflater.inflate(R.layout.brand_gridview_layout, null);
        }

        TextView name = v.findViewById(R.id.brand_grid_name);
        TextView origin = v.findViewById(R.id.brand_grid_origin);
        ImageView logo = v.findViewById(R.id.brand_grid_image);

        String brand = brandObjectList.get(position).getName().toLowerCase();

        if(position != (brandObjectList.size() - 1)){
            String image_url = APIConstants.PRODUCT_IMAGE+brandObjectList.get(position).getImage();
//            Picasso.with(context).load(image_url).into(logo);
            Picasso.get().load(image_url).into(logo);
            name.setText(brandObjectList.get(position).getName());
            origin.setText(brandObjectList.get(position).getOrigin());

        } else {
            brand = "not found!";
            name.setText("No specific Brand");
            logo.setVisibility(View.INVISIBLE);
            origin.setText("");
        }
        final String brandName = brand;
        // Get the TextView and ImageView from CustomView for displaying item

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(context, LoadingActivity.class);
                intent.putExtra("brand_name", brandName);
                intent.putExtra("activity","products");
                context.startActivity(intent);*/
                MainActivity.brand = brandName;
                Fragment fragment = new ProductFragment();
                FragmentManager fragmentManager = ((FragmentActivity)view.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
                MainActivity.tvHomeToolbarTitle.setText(context.getString(R.string.title_product_directory));

            }
        });
        return v;

    }
}
