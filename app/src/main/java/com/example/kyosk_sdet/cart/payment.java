package com.example.kyosk_sdet.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kyosk_sdet.Home.HomeScreen;
import com.example.kyosk_sdet.R;
import com.example.kyosk_sdet.Utility.AppUtilits;
import com.example.kyosk_sdet.Utility.Constant;
import com.example.kyosk_sdet.Utility.NetworkUtility;
import com.example.kyosk_sdet.Utility.SharedPreferenceActivity;
import com.example.kyosk_sdet.WebResponse.payAPI;
import com.example.kyosk_sdet.WebServices.ServiceWrapper;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class payment extends AppCompatActivity {
    private String TAG = "payment";

    EditText payment_amount;
    TextView thanks, request, continuebtn ;
    Button home, order;
     RelativeLayout relative_lay2, relative_lay3;

     Context context;
     SharedPreferenceActivity sharedPreferenceActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        context = this;
        sharedPreferenceActivity = new SharedPreferenceActivity(context);

        getSupportActionBar().setHomeButtonEnabled(true);

        relative_lay3 = (RelativeLayout) findViewById(R.id.relative_lay3);
        relative_lay2 = (RelativeLayout) findViewById(R.id.relative_lay2);

        thanks = (TextView) findViewById(R.id.thanks);
        request = (TextView) findViewById(R.id.your_request);
        payment_amount = (EditText) findViewById(R.id.amount);
        continuebtn = (TextView) findViewById(R.id.continuebtn);
        home=findViewById(R.id.homebutton);
        order=findViewById(R.id.shopimage);



        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    makepayment();
                }


        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(payment.this, OrderHistory.class);
                startActivity(intent);
                finish();*/

            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(payment.this, HomeScreen.class);
                startActivity(intent);
                finish();

            }
        });
    }
    public void makepayment(){
        if (!NetworkUtility.isNetworkConnected(payment.this)){
            Toast.makeText(getApplicationContext(),"Network error",Toast.LENGTH_LONG).show();

        }else {

            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<payAPI> makepaymentAPICall=serviceWrapper.makepaymentcall(Constant.identity, sharedPreferenceActivity.getItem(Constant.USER_DATA),  sharedPreferenceActivity.getItem(Constant.USER_order_id),
                    sharedPreferenceActivity.getItem(Constant.TOTAL_TOTAL), payment_amount.getText().toString());
            makepaymentAPICall.enqueue(new Callback<payAPI>() {
                @Override
                public void onResponse(Call<payAPI> call, Response<payAPI> response) {

                    if(Integer.valueOf(String.valueOf(payment_amount.getText()))<(Integer.valueOf(sharedPreferenceActivity.getItem(Constant.pin)))){
                        Toast.makeText(getApplicationContext(),"Minimum amount expected\n Amount : "+ String.valueOf(sharedPreferenceActivity.getItem(Constant.pin)),Toast.LENGTH_LONG).show();

                        Log.e("amnt", String.valueOf(payment_amount.getText()));
                        Log.e("amnt", String.valueOf(sharedPreferenceActivity.getItem(Constant.TOTAL_TOTAL)));

                    }else {

                        Log.e(TAG, "response is " + response.body() + "  ---- " + new Gson().toJson(response.body()));
                        //  Log.e(TAG, "  ss sixe 1 ");
                        if (response.body() != null && response.isSuccessful()) {
                            //    Log.e(TAG, "  ss sixe 2 ");
                            if (response.body().getStatus() == 1) {


                                    relative_lay3.setVisibility(View.VISIBLE);
                                    relative_lay2.setVisibility(View.GONE);

                            } else {

                                AppUtilits.displayMessage(payment.this, response.body().getMsg());

                            }

                        } else {
                            AppUtilits.displayMessage(payment.this, getString(R.string.network_error));

                        }
                    }


                }

                @Override
                public void onFailure(Call<payAPI> call, Throwable t) {

                    Log.e(TAG, "  fail- to make payment"+ t.toString());
                    Toast.makeText(getApplicationContext(),"Failed to make payment",Toast.LENGTH_LONG).show();


                }
            });

        }


    }

}

