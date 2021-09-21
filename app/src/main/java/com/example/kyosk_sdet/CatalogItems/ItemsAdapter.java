package com.example.kyosk_sdet.CatalogItems;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kyosk_sdet.Home.HomeScreen;
import com.example.kyosk_sdet.Orders.OrderHistory_ViewDetails;
import com.example.kyosk_sdet.R;
import com.example.kyosk_sdet.Utility.AppUtilits;
import com.example.kyosk_sdet.Utility.Constant;
import com.example.kyosk_sdet.Utility.NetworkUtility;
import com.example.kyosk_sdet.Utility.SharedPreferenceActivity;
import com.example.kyosk_sdet.WebResponse.AddtoCart;
import com.example.kyosk_sdet.WebServices.ServiceWrapper;
import com.example.kyosk_sdet.cart.CartDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context mContext;
    SharedPreferenceActivity sharedPreferenceActivity;
    private List<ItemModel> mitemModelList;

    private String TAG ="item_adapter";
    private int mScrenwith;

    public ItemsAdapter(Context context, List<ItemModel> list, int screenwidth ){
        mContext = context;
        sharedPreferenceActivity = new SharedPreferenceActivity(mContext);
        mitemModelList = list;
        mScrenwith =screenwidth;

    }

    private class ProductHolder extends RecyclerView.ViewHolder {
        ImageView prod_img;
        TextView prod_name,prod_price,prod_stock;
        CardView cardView;
        Button addtocarButton;

        public ProductHolder(View itemView) {
            super(itemView);
            prod_img = (ImageView) itemView.findViewById(R.id.prod_img);
            prod_name = (TextView) itemView.findViewById(R.id.prod_name);
            prod_price = (TextView) itemView.findViewById(R.id.prod_price);
            prod_stock = (TextView) itemView.findViewById(R.id.prod_stock);
            addtocarButton = itemView.findViewById(R.id.addtocarButton);

            cardView = (CardView) itemView.findViewById(R.id.card_view);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( mScrenwith - (mScrenwith/100*70), LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(10,10,10,10);
            cardView.setLayoutParams(params);
            cardView.setPadding(5,5,5,5);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent,false);
        Log.e(TAG, "  view created ");
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemModel model = mitemModelList.get(position);
        Log.e(TAG, " assign value ");
        ((ProductHolder) holder).prod_name.setText(model.getProd_name());
        ((ProductHolder) holder).prod_price.setText("Ksh: "+model.getPrice());
        ((ProductHolder) holder).prod_stock.setText("Stock: "+model.getStock());


        // imageview glider lib to get imagge from url
        Glide.with(mContext)
                .load(Constant.BASE_URL+model.getImg_ulr())
                .into(((ProductHolder) holder).prod_img);



        ((ItemsAdapter.ProductHolder) holder).addtocarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // store produ id with user id on server  and get quate id as response and store it on share preferernce
                if (model.getStock().equals("0")){

                    Toast.makeText(mContext,"Sorry,product is out out stock", Toast.LENGTH_LONG).show();
                }else{
                    addtocartapi(model.getProd_id() );
                }


            }
        });

    }

    private void addtocartapi(String prod_id) {

            final AlertDialog progressbar = AppUtilits.createProgressBar(mContext,"Adding To Cart");

            if (!NetworkUtility.isNetworkConnected(mContext)){
                AppUtilits.displayMessage(mContext,  "Network Error");

                AppUtilits.destroyDialog(progressbar);
            }else {

                //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
                ServiceWrapper service = new ServiceWrapper(null);
                Call<AddtoCart> call = service.addtoCartCall(Constant.identity, prod_id, sharedPreferenceActivity.getItem(Constant.USER_DATA)  );
                call.enqueue(new Callback<AddtoCart>() {
                    @Override
                    public void onResponse(Call<AddtoCart> call, Response<AddtoCart> response) {
                        Log.e(TAG, "prod_id "+ prod_id);

                        if (response.body() != null && response.isSuccessful()) {
                            if (response.body().getStatus() == 1) {

                                AppUtilits.destroyDialog(progressbar);
                                sharedPreferenceActivity.putItem(Constant.QUOTE_ID, response.body().getInformation().getQouteId());
                                Toast.makeText(mContext,response.body().getMsg(), Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(mContext, HomeScreen.class);
                                mContext.startActivity(intent);
                              /*  AppUtilits.UpdateCartCount(ProductDetails.this,mainmenu);*/

                            }else {
                                AppUtilits.destroyDialog(progressbar);
                                AppUtilits.displayMessage(mContext, "Failed to add item to cart");
                            }
                        }else {
                            AppUtilits.destroyDialog(progressbar);
                            AppUtilits.displayMessage(mContext, "Something went wrong please try again");
                        }


                    }

                    @Override
                    public void onFailure(Call<AddtoCart> call, Throwable t) {
                        // Log.e(TAG, "  fail- add to cart item "+ t.toString());
                        AppUtilits.destroyDialog(progressbar);
                        AppUtilits.displayMessage(mContext, "Something went wrong please try again");
                    }
                });
            }
        }


    @Override
    public int getItemCount() {
        return mitemModelList.size();
    }


}
