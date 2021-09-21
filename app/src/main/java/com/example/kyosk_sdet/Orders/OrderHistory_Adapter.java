package com.example.kyosk_sdet.Orders;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kyosk_sdet.R;
import com.example.kyosk_sdet.Utility.SharedPreferenceActivity;

import java.util.ArrayList;
import java.util.List;


public class OrderHistory_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<orderhistory_model> history_model;
    private Context mContext;
    SharedPreferenceActivity sharedPreferenceActivity;
    private String TAG = "orderhistory_adapter";

    private ArrayList<RelativeLayout> addrlayoutsList=  new ArrayList<>();
    private ArrayList<ImageView> imagelist = new ArrayList<>();

    public OrderHistory_Adapter (Context context, List<orderhistory_model> addressModels) {
        this.history_model = addressModels;
        this.mContext = context;
        sharedPreferenceActivity = new SharedPreferenceActivity(mContext);

    }
    private class OrderHistoryItemView extends RecyclerView.ViewHolder {
        TextView order_id,  order_shipping, order_price, order_date, order_viewdetails,generate_receipts,getreceipts;


        public OrderHistoryItemView(View itemView) {
            super(itemView);
            order_id = (TextView) itemView.findViewById(R.id.order_id);
            order_price = (TextView) itemView.findViewById(R.id.order_price);
            order_shipping = (TextView) itemView.findViewById(R.id.order_shipping);
            order_date = (TextView) itemView.findViewById(R.id.order_date);
            order_viewdetails = (TextView) itemView.findViewById(R.id.order_viewdetails);
            generate_receipts = (TextView) itemView.findViewById(R.id.receipts);
            getreceipts = (TextView) itemView.findViewById(R.id.getreceipts);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_orderhistory_item, parent,false);
        //Log.e(TAG, "  view created ");
        return new OrderHistoryItemView(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        Log.e(TAG, "bind view "+ position);
        final orderhistory_model model =  history_model.get(position);

        ((OrderHistoryItemView) holder).order_id.setText(model.getorder_id());


        ((OrderHistoryItemView) holder).order_date.setText(model.getdate());
        // ((OrderAddressItemView) holder).address_layoutmain.setTag( model.getaddress_id());
        ((OrderHistoryItemView) holder).order_viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(TAG, "  user select the order id " + model.getorder_id() );


                Intent intent = new Intent(mContext, OrderHistory_ViewDetails.class);
                intent.putExtra("order_id", model.getorder_id());
                intent.putExtra("address", model.getshippingaddress());
                mContext.startActivity(intent);

            }
        });

                ((OrderHistoryItemView) holder).generate_receipts.setText(sharedPreferenceActivity.getItem("deliverydate"));
        ((OrderHistoryItemView) holder).getreceipts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(TAG, "  user select the order id " + model.getorder_id() );


                /*Intent intent = new Intent(mContext, generate_receipts.class);
                intent.putExtra("order_id", model.getorder_id());
                intent.putExtra("address", model.getshippingaddress());
                mContext.startActivity(intent);*/

            }
        });

     /*   ((OrderHistoryItemView) holder).generate_receipts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(TAG, "  user select the order id " + model.getorder_id() );


                Intent intent = new Intent(mContext, generate_receipts.class);
                intent.putExtra("order_id", model.getorder_id());
                intent.putExtra("address", model.getshippingaddress());
                mContext.startActivity(intent);

            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return history_model.size();
    }
}

