package com.example.almasud.fundamental.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.almasud.fundamental.R;

public class FragmentActivity extends AppCompatActivity implements FragmentOne.MyInterface {
    private static final String TAG = FragmentActivity.class.getSimpleName();
    FragmentManager fm;
    FragmentTransaction ft;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Log.e(TAG, "onCreate is called");

        fragment = new FragmentOne();
        // Initial Fragment
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fragmentContainer, fragment);
        ft.commit();
        // Passing data from activity to fragment
        Bundle b = new Bundle();
        b.putString("greetings", "Welcome Fragment One");
        fragment.setArguments(b);
    }

    public void changeFragment(View view) {
        switch (view.getId()) {
            case R.id.fragmentBtn1:
                fragment = new FragmentOne();
                break;
            case R.id.fragmentBtn2:
                fragment = new FragmentTwo();
                break;
        }
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.addToBackStack(null);  // Add Fragment into Stack
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart is called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume is called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause is called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop is called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart is called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy is called");
    }

    @Override
    public void passValue(String msg) {
        fragment = new FragmentTwo();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.addToBackStack(null);  // Add Fragment into Stack
        ft.commit();
        // Passing data from activity to fragment
        Bundle b = new Bundle();
        b.putString("greetings", msg);
        fragment.setArguments(b);
    }
}
