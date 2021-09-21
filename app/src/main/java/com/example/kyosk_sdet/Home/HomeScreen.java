package com.example.kyosk_sdet.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kyosk_sdet.CatalogItems.ItemModel;
import com.example.kyosk_sdet.CatalogItems.ItemsAdapter;
import com.example.kyosk_sdet.CatalogItems.MyDividerItemDecoration;
import com.example.kyosk_sdet.Orders.OrderHistory;
import com.example.kyosk_sdet.R;
import com.example.kyosk_sdet.Utility.AppUtilits;
import com.example.kyosk_sdet.Utility.Constant;
import com.example.kyosk_sdet.Utility.NetworkUtility;
import com.example.kyosk_sdet.Utility.SharedPreferenceActivity;
import com.example.kyosk_sdet.WebResponse.NewProductRes;
import com.example.kyosk_sdet.WebServices.ServiceWrapper;
import com.example.kyosk_sdet.cart.CartDetails;
import com.example.kyosk_sdet.cart.payment;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreen extends AppCompatActivity {

    SharedPreferenceActivity sharedPreferenceActivity;
    Context context;
    TextView welcome;
    RecyclerView recycler_items;
    TextView viewcart, vieworders;

    String TAG = "Home Screen";
    private ItemsAdapter items_adapter;
    private ArrayList<ItemModel> itemModelList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        context = this;
        sharedPreferenceActivity = new SharedPreferenceActivity(this);

        welcome = findViewById(R.id.welcome);

        viewcart = findViewById(R.id.viewcart);
        vieworders = findViewById(R.id.vieworders);
        welcome.setText("Welcome" + " " + sharedPreferenceActivity.getItem(Constant.USER_name));


        recycler_items = findViewById(R.id.recycler_items);

        LinearLayoutManager mLayoutManger3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recycler_items.setLayoutManager(mLayoutManger3);
        recycler_items.setItemAnimator(new

        DefaultItemAnimator());

        items_adapter=new ItemsAdapter(this,itemModelList, GetScreenWidth());
        recycler_items.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recycler_items.setAdapter(items_adapter);

        viewcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeScreen.this, CartDetails.class);
                startActivity(intent);
                finish();
            }
        });
        vieworders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, OrderHistory.class);
                startActivity(intent);
                finish();
            }
        });

        //Fetch product details from DB
        getItemsRes();
    }

    private void getItemsRes() {


            if (!NetworkUtility.isNetworkConnected(HomeScreen.this)) {
                AppUtilits.displayMessage(HomeScreen.this, getString(R.string.network_not_connected));

            } else {
                ServiceWrapper service = new ServiceWrapper(null);
                Call<NewProductRes> call = service.getNewProductRes(Constant.identity);
                call.enqueue(new Callback<NewProductRes>() {
                    @Override
                    public void onResponse(Call<NewProductRes> call, Response<NewProductRes> response) {
                        Log.e(TAG, " response is " + response.body().getInformation().toString());
                        if (response.body() != null && response.isSuccessful()) {
                            if (response.body().getStatus() == 1) {
                                if (response.body().getInformation().size() > 0) {

                                    itemModelList.clear();
                                    for (int i = 0; i < response.body().getInformation().size(); i++) {

                                        Log.e(TAG, "Image response is " + response.body().getInformation().get(i).getImgUrl());
                                        itemModelList.add(new ItemModel(response.body().getInformation().get(i).getId(), response.body().getInformation().get(i).getName(),
                                                response.body().getInformation().get(i).getImgUrl(),response.body().getInformation().get(i).getPrice(),response.body().getInformation().get(i).getStock()));

                                    }

                                    items_adapter.notifyDataSetChanged();
                                }

                            } else {
                                Log.e(TAG, "failed to get products " + response.body().getMsg());
                                // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                            }
                        } else {
                            // AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.failed_request));

                            //  getNewProdRes();
                        }
                    }

                    @Override
                    public void onFailure(Call<NewProductRes> call, Throwable t) {
                        Log.e(TAG, "fail new prod " + t.toString());

                    }
                });

            }

        }




    public int GetScreenWidth(){
        int  width =100;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager =  (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;

        return width;

    }
}
