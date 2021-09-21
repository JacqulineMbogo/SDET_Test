package com.example.kyosk_sdet.Utility;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyosk_sdet.R;

import java.util.Calendar;


public class AppUtilits {
        public static void displayMessage(Context mContext, String message){

            MessageDialog messageDialog = null;
            if (messageDialog == null)
                messageDialog = new MessageDialog(mContext, message);
            messageDialog.displayMessageShow();

        }


    public static String getCurrentDates()
    {
        Calendar calendar   =   Calendar.getInstance();
        //To keep consistence with SQL, check and append a zero when the day has one digit.
        String day;
        String month;


        day   =   String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));


        month   =   String.valueOf(calendar.get(Calendar.MONTH)+1);

        return String.format("%s-%s-%s", calendar.get(Calendar.YEAR), month, day);
    }
    public static AlertDialog createProgressBar(Context context, String message)
    {
        LayoutInflater layoutInflater   =   (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view   =   layoutInflater.inflate(R.layout.progressbar, null, false);


        AlertDialog.Builder alertDialog =   new AlertDialog.Builder(context);
        alertDialog.setView(view);
        alertDialog.setCancelable(false);
        ((TextView)view.findViewById(R.id.dialogtext)).setText(message);
        AlertDialog dialog  =   alertDialog.create();
        dialog.show();

        return dialog;
    }


   /* public static void createToaster(Context context, String message, int duration)
    {
        LayoutInflater layoutInflater   =   (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view   =   layoutInflater.inflate(R.layout.toaster_layoru, null, false);
        Toast toast =   new Toast(context);
        toast.setView(view);

        ((TextView)view.findViewById(R.id.toaster_txt_message)).setText(message);


        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        toast.show();

    }*/

    public static void destroyDialog(AlertDialog dialog)
    {
        dialog.dismiss();
    }



}
