package com.caraguna.alquran_apps.util;

import android.app.ProgressDialog;

import com.caraguna.alquran_apps.R;

public class PgDialog {

    public static void show(ProgressDialog progressDialog){
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public static void hide(ProgressDialog progressDialog){
        progressDialog.dismiss();
    }
}
