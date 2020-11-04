package com.caraguna.alquran_apps.fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caraguna.alquran_apps.R;

public class PopUpLauncher extends Fragment implements View.OnClickListener {
    private ImageView btnClose;
    TextView getStarted;
    private ConstraintLayout cardView;

    public PopUpLauncher() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pop_up_launcher, container, false);

        btnClose = view.findViewById(R.id.btnClose);
        getStarted = view.findViewById(R.id.getStarted);
        cardView = view.findViewById(R.id.popUpContainer);

        btnClose.setOnClickListener(this);
        getStarted.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnClose:
                cardView.setVisibility(View.GONE);
                break;
            case R.id.getStarted:
                cardView.setVisibility(View.GONE);
                break;
        }
    }
}
