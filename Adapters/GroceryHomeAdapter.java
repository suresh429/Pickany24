package com.prism.pickany247.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prism.pickany247.R;
import com.prism.pickany247.Response.GroceryProductResponse;
import com.prism.pickany247.Response.StationeryResponse;
import com.prism.pickany247.StationeryModule.ProductDetailsActivity;

import java.util.List;
import java.util.StringTokenizer;

public class GroceryHomeAdapter extends RecyclerView.Adapter<GroceryHomeAdapter.MyViewHolder>{
    private Context mContext;
    private List<GroceryProductResponse.FilteredProductsBean> homeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView txtPrice,txtName;
        public LinearLayout linearLayout;


        public MyViewHolder(View view) {
            super(view);

            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            txtPrice = (TextView) view.findViewById(R.id.productPrice);
            txtName = (TextView) view.findViewById(R.id.productTitle);
            linearLayout=(LinearLayout)view.findViewById(R.id.parentLayout);


        }
    }

    public GroceryHomeAdapter(Context mContext, List<GroceryProductResponse.FilteredProductsBean> homekitchenList) {
        this.mContext = mContext;
        this.homeList = homekitchenList;
    }

    @Override
    public GroceryHomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stationery_cat_card, parent, false);

        return new GroceryHomeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GroceryHomeAdapter.MyViewHolder holder, final int position) {
        final GroceryProductResponse.FilteredProductsBean home = homeList.get(position);

        // loading album cover using Glide library
        Glide.with(mContext).load(home.getImagePath()+home.getSingleImage()).into(holder.thumbnail);
        holder.txtName.setText(home.getProduct_name());

        String firstPrice="";
        String firstcapacity="";
        if(home.getIncl_price()!=null){
            StringTokenizer stringTokenizer =new StringTokenizer(home.getIncl_price());
             firstPrice = stringTokenizer.nextToken(",");
        }else{
            // null response or Exception occur
        }

        if (home.getCapacity()!=null){
            StringTokenizer stringTokenizer1 =new StringTokenizer(home.getCapacity());
             firstcapacity = stringTokenizer1.nextToken(",");
        }




        holder.txtPrice.setText("\u20B9"+firstPrice+"  ("+firstcapacity+")");
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    Activity activity = (Activity) mContext;
                Intent intent = new Intent(mContext, ProductDetailsActivity.class);
                intent.putExtra("productId",home.getProduct_id());
                intent.putExtra("productName",home.getProduct_name());
                intent.putExtra("module",home.getModule());
                Log.e("MODULE",""+home.getModule());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                // activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


            }
        });


    }


    @Override
    public int getItemCount() {
        return homeList.size();
    }



}
