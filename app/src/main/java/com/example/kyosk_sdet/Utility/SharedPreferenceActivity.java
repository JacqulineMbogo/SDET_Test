package com.example.kyosk_sdet.Utility;

import android.content.Context;
import android.content.SharedPreferences;



public class SharedPreferenceActivity {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferenceActivity(Context context)
    {
        this.context        =   context;
        sharedPreferences   =   context.getSharedPreferences(Constant.SHARE_PREF, Context.MODE_PRIVATE);
        editor              =   sharedPreferences.edit();
    }

    public String getItem(String name)
    {
        if(sharedPreferences.getString(name, "").trim().length()>0)
        {
            return sharedPreferences.getString(name, "");
        }
        else
        {
            return "";
        }
    }

    public boolean putItem(String name, int value)
    {
        editor.putInt(name, value);
        return editor.commit();
    }

    public boolean putItem(String name, float value)
    {
        editor.putFloat(name, value);
        return editor.commit();
    }

    public int getItemInt(String name)
    {
        return sharedPreferences.getInt(name, 0);
    }

    public float getItemDouble(String name)
    {
        return sharedPreferences.getFloat(name, 0);
    }

    public boolean getItemBoolean(String name)
    {
        return sharedPreferences.getBoolean(name, false);
    }

    public boolean putItem(String name, boolean value)
    {
        if(sharedPreferences.getBoolean(name, false))
        {
            editor.remove(name);
            editor.putBoolean(name, value);
        }
        else
        {
            editor.putBoolean(name, value);
        }

        return editor.commit();
    }

    public  boolean putItem(String name, String value)
    {
        if(sharedPreferences.getString(name, "").equals("") || sharedPreferences.getString(name, null)==null)
        {
            editor.putString(name, value);
        }
        else
        {
            editor.remove(name);
            editor.putString(name, value);
        }

        return editor.commit();
    }

    public boolean removeItem(String name)
    {
        editor.remove( name);
        return editor.commit();
    }

    public void clearSharePref()

    {
        editor.clear();
    }
}