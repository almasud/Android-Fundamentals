package com.example.almasud.fundamental.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.almasud.fundamental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {
    private static final String TAG = FragmentTwo.class.getSimpleName();
    private Context context;

    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach is called");
        // Assign the context of activity where this fragment is attached
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate is called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView is called");
        // Receive data from activity
        if (getArguments() != null && getArguments().containsKey("greetings")) {
            Toast.makeText(context, getArguments().getString("greetings"), Toast.LENGTH_LONG).show();
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_two, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated is called");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart is called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume is called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause is called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop is called");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView is called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy is called");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach is called");
    }

}
