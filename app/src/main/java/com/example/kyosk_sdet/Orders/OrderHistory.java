package com.example.kyosk_sdet.Orders;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kyosk_sdet.Home.HomeScreen;
import com.example.kyosk_sdet.R;
import com.example.kyosk_sdet.Utility.AppUtilits;
import com.example.kyosk_sdet.Utility.Constant;
import com.example.kyosk_sdet.Utility.NetworkUtility;
import com.example.kyosk_sdet.Utility.SharedPreferenceActivity;
import com.example.kyosk_sdet.WebResponse.OrderHistoryAPI;
import com.example.kyosk_sdet.WebServices.ServiceWrapper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderHistory extends AppCompatActivity {

    private String TAG = "orderhistory";
    private RecyclerView recyclerView_order;
    private ArrayList<orderhistory_model> Models = new ArrayList<>();
    private OrderHistory_Adapter adapter;
    Context context;
    SharedPreferenceActivity sharedPreferenceActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderhistory);

        context = this;
        sharedPreferenceActivity = new SharedPreferenceActivity(context);


        recyclerView_order = (RecyclerView) findViewById(R.id.recycler_orderhistory);
        LinearLayoutManager mLayoutManger3 = new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false);
        recyclerView_order.setLayoutManager(mLayoutManger3);
        recyclerView_order.setItemAnimator(new DefaultItemAnimator());

        adapter = new OrderHistory_Adapter( OrderHistory.this, Models);

        recyclerView_order.setAdapter(adapter);


        getUserOrderHistory();

    }

    public void getUserOrderHistory(){
        if (!NetworkUtility.isNetworkConnected(OrderHistory.this)){
            Toast.makeText(getApplicationContext(),"Network error",Toast.LENGTH_LONG).show();

        }else {
            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<OrderHistoryAPI> call = service.getorderhistorycall(Constant.identity, sharedPreferenceActivity.getItem(Constant.USER_DATA));
            call.enqueue(new Callback<OrderHistoryAPI>() {
                @Override
                public void onResponse(Call<OrderHistoryAPI> call, Response<OrderHistoryAPI> response) {
                    Log.e(TAG, "response is " + response.body() + "  ---- " + new Gson().toJson(response.body()));
                    //  Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {
                        //    Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {
                            //      Log.e(TAG, "  ss sixe 3 ");
                            Models.clear();

                            if (response.body().getInformation().size()>0){

                                for (int i =0; i<response.body().getInformation().size(); i++){

                                    Models.add(  new orderhistory_model(response.body().getInformation().get(i).getOrderId(), response.body().getInformation().get(i).getShippingaddress(),
                                            response.body().getInformation().get(i).getPrice(), response.body().getInformation().get(i).getDate()));



                                }
                                adapter.notifyDataSetChanged();
                            }else{
                                AppUtilits.displayMessage(OrderHistory.this, "No orders made yet");
                            }

                        } else {
                            AppUtilits.displayMessage(OrderHistory.this, response.body().getMsg());

                        }

                    } else {
                        AppUtilits.displayMessage(OrderHistory.this, getString(R.string.network_error));

                    }
                }
                @Override
                public void onFailure(Call<OrderHistoryAPI> call, Throwable t) {
                    Log.e(TAG, "  fail- add to cart item "+ t.toString());
                    Toast.makeText(getApplicationContext(),"Failed to get order history",Toast.LENGTH_LONG).show();

                }
            });


        }


    }

    @Override
    public void onBackPressed() {


        Intent intent1 = new Intent(OrderHistory.this, HomeScreen.class);

        startActivity(intent1);



    }

}
