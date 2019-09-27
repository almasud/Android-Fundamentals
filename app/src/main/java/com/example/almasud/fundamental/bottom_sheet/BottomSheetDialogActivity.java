package com.example.almasud.fundamental.bottom_sheet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.almasud.fundamental.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetDialogActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout shareLinearLayout, uploadLinearLayout, copyLinearLayout;
    private BottomSheetDialog bottomSheetDialog;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_dialog);

        textView = findViewById(R.id.textViewBottomSheetDialog);
        createBottomSheetDialog();
    }

    private void createBottomSheetDialog() {
        if (bottomSheetDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog, null);
            shareLinearLayout = view.findViewById(R.id.shareLinearLayout);
            uploadLinearLayout = view.findViewById(R.id.uploadLinearLayout);
            copyLinearLayout = view.findViewById(R.id.copyLinearLayout);
            shareLinearLayout.setOnClickListener(this);
            uploadLinearLayout.setOnClickListener(this);
            copyLinearLayout.setOnClickListener(this);

            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(view);
        }
    }

    public void showBottomSheetDialog(View view) {
        bottomSheetDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shareLinearLayout:
                textView.setText("SHARE");
                bottomSheetDialog.dismiss();
                break;
            case R.id.uploadLinearLayout:
                textView.setText("UPLOAD");
                bottomSheetDialog.dismiss();
                break;
            case R.id.copyLinearLayout:
                textView.setText("COPY");
                bottomSheetDialog.dismiss();
                break;
        }
    }
}
