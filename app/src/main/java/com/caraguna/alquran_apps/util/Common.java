package com.caraguna.alquran_apps.util;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class Common {
    public static void volleyErrorHandle(Context context, VolleyError error){
        if (error instanceof NetworkError){
            Toast.makeText(context, Configuration.VOLLEY_ERROR_CONNECTION, Toast.LENGTH_SHORT).show();
        }else if(error instanceof ServerError){
            Toast.makeText(context, Configuration.VOLLEY_SERVER_ERROR, Toast.LENGTH_SHORT).show();
        }else if(error instanceof AuthFailureError){
            Toast.makeText(context, Configuration.VOLLEY_AUTH_ERROR, Toast.LENGTH_SHORT).show();
        }else if(error instanceof ParseError){
            Toast.makeText(context, Configuration.VOLLEY_PARSE_ERROR, Toast.LENGTH_SHORT).show();
        }else if(error instanceof NoConnectionError){
            Toast.makeText(context, Configuration.VOLLEY_NO_INTERNET, Toast.LENGTH_SHORT).show();
        }else if (error instanceof TimeoutError){
            Toast.makeText(context, Configuration.VOLLEY_TIME_OUT, Toast.LENGTH_SHORT).show();
        }
    }
}
