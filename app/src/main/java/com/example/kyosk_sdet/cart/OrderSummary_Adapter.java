package com.example.kyosk_sdet.cart;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.kyosk_sdet.R;

import java.util.List;


public class OrderSummary_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Cartitem_Model> cartitem_models;
    private Context mContext;
    private String TAG = "order_adapter";

    public OrderSummary_Adapter(Context context, List<Cartitem_Model> cartitemModels) {
        this.cartitem_models = cartitemModels;
        this.mContext = context;

    }
    private class ordersummaryItemView extends RecyclerView.ViewHolder {
        TextView prod_name,  prod_qty, prod_price;


        public  ordersummaryItemView(View itemView) {
            super(itemView);
            prod_name = (TextView) itemView.findViewById(R.id.prod_name);
            prod_price = (TextView) itemView.findViewById(R.id.prod_price);
            prod_qty = (TextView) itemView.findViewById(R.id.prod_qty);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ordersum_item, parent,false);
        Log.e(TAG, "  view created ");
        return new ordersummaryItemView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Log.e(TAG, "bind view "+ position);
        final Cartitem_Model model =  cartitem_models.get(position);

        ((ordersummaryItemView) holder).prod_name.setText(model.getProd_name());
        ((ordersummaryItemView) holder).prod_price.setText(model.getPrice());
        ((ordersummaryItemView) holder).prod_qty.setText(model.getQty());


        Log.e(TAG, "qqty "+  cartitem_models.get(position));




    }

    @Override
    public int getItemCount() {
        return cartitem_models.size();
    }
}