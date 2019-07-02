package com.example.almasud.fundamental.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.almasud.fundamental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {
    private static final String TAG = FragmentOne.class.getSimpleName();
    private Context context;
    private MyInterface myInterface;
    private Button fOneBtn;

    public FragmentOne() {
        // Required empty public constructor
    }
    /*
     Data passing interface from this fragment to another fragment
     via an activity using a callback method
     */
    public interface MyInterface {
        // Callback method for passing data
        void passValue(String msg);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach is called");
        // Assign the context of activity where this fragment is attached
        this.context = context;
        // Assign the attached activity context who implements ClickListener
        myInterface = (MyInterface) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate is called");
        // Receive data from activity
        if (getArguments() != null && getArguments().containsKey("greetings")) {
            Toast.makeText(context, getArguments().getString("greetings"), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView is called");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_one, container, false);
        fOneBtn = view.findViewById(R.id.fragOneBtn);
        fOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.passValue("Welcome fragment two");
            }
        });
        return view;
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
