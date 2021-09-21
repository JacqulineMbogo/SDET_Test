package com.example.kyosk_sdet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kyosk_sdet.Home.HomeScreen;
import com.example.kyosk_sdet.Utility.AppUtilits;
import com.example.kyosk_sdet.Utility.Constant;
import com.example.kyosk_sdet.Utility.NetworkUtility;
import com.example.kyosk_sdet.Utility.SharedPreferenceActivity;
import com.example.kyosk_sdet.WebResponse.UserSignInRes;
import com.example.kyosk_sdet.WebServices.ServiceWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LauncherActivity extends AppCompatActivity {
    private String TAG ="splashAcctivity";

    SharedPreferenceActivity sharedPreferenceActivity;
    Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        context = this;
        sharedPreferenceActivity = new SharedPreferenceActivity(this);
        init();
        Log.e(TAG, " splash start showing");
    }
    public void init(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // if user registered user
                // then show home screen
                // else  Create new test user

                Log.e(TAG, "  counter running ");
                if (sharedPreferenceActivity.getItem(Constant.USER_DATA).equalsIgnoreCase("")){
                    // Create new test user
                    sendUserLoginData();
                }else {
                    // Navigate to home sscreen
                    Intent intent = new Intent(LauncherActivity.this, HomeScreen.class);
                    startActivity(intent);                finish();

                }

            }
        }, 5000 );

    }

    public void sendUserLoginData(){

        final AlertDialog progressbar = AppUtilits.createProgressBar(this,"Setting Up Test User..");


        if (!NetworkUtility.isNetworkConnected(LauncherActivity.this)){
            Toast.makeText(getApplicationContext(),"Network error",Toast.LENGTH_LONG).show();
            AppUtilits.destroyDialog(progressbar);

        }else {

            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<UserSignInRes> userSigninCall = serviceWrapper.UserSigninCall(Constant.identity);
            userSigninCall.enqueue(new Callback<UserSignInRes>() {
                @Override
                public void onResponse(Call<UserSignInRes> call, Response<UserSignInRes> response) {

                    Log.d(TAG, "reponse : "+ response.toString());
                    if (response.body()!= null && response.isSuccessful()){
                        if (response.body().getStatus() ==1){
                            Log.e(TAG, "  user data "+  response.body().getInformation());
                            sharedPreferenceActivity.putItem(Constant.USER_DATA, response.body().getInformation().getUserId());
                            sharedPreferenceActivity.putItem(Constant.USER_name, response.body().getInformation().getFullname());
                            sharedPreferenceActivity.putItem(Constant.USER_email, response.body().getInformation().getEmail());
                            sharedPreferenceActivity.putItem(Constant.USER_phone, response.body().getInformation().getPhone());


                            AppUtilits.destroyDialog(progressbar);
                            // start home activity
                            Intent intent = new Intent(LauncherActivity.this, HomeScreen.class);
                            //intent.putExtra("userid", "sdfsd");
                            startActivity(intent);
                            finish();





                        }else  if (response.body().getStatus() ==0){
                            AppUtilits.displayMessage(LauncherActivity.this,  response.body().getMsg());
                            AppUtilits.destroyDialog(progressbar);
                        }
                    }else {
                        AppUtilits.displayMessage(LauncherActivity.this,  response.body().getMsg());
                        Toast.makeText(getApplicationContext(),"Request failed",Toast.LENGTH_LONG).show();
                        AppUtilits.destroyDialog(progressbar);

                    }
                }

                @Override
                public void onFailure(Call<UserSignInRes> call, Throwable t) {
                    Log.e(TAG, " failure "+ t.toString());
                    Toast.makeText(getApplicationContext(),"Request failed",Toast.LENGTH_LONG).show();
                    AppUtilits.destroyDialog(progressbar);

                }
            });




        }








    }
}
