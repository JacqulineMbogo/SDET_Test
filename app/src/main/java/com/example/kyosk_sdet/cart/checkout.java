package com.example.kyosk_sdet.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kyosk_sdet.R;


public class checkout extends AppCompatActivity {


    private String TAG =" checkout";
    private TextView mpesabutton;
    private ImageView cod;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout );

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mpesabutton= findViewById(R.id.mpesabutton);


        mpesabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(checkout.this,PlaceOrderActivity.class);
                startActivity(intent);

            }
        });





    }

}
