package com.example.almasud.fundamental.menu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.almasud.fundamental.R;

public class MenuActivity extends AppCompatActivity {
    private boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // Checked default menu of background.
        menu.findItem(R.id.menu_white).setChecked(true);

        return true;
    }

    // Changing menu items at runtime
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem loginItem = menu.findItem(R.id.menu_login);
        MenuItem logoutItem = menu.findItem(R.id.menu_logout);

        if (isLoggedIn) {
            loginItem.setVisible(false);
            logoutItem.setVisible(true);
        } else {
            loginItem.setVisible(true);
            logoutItem.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_home:
                break;
            case R.id.menu_settings:
                break;
            case R.id.menu_login:
                isLoggedIn = true;
                break;
            case R.id.menu_logout:
                isLoggedIn = false;
                break;
            case R.id.menu_white:
                findViewById(R.id.menu_activity).setBackgroundColor(Color.WHITE);
                item.setChecked(true);
                break;
            case R.id.menu_greed:
                findViewById(R.id.menu_activity).setBackgroundColor(Color.GREEN);
                item.setChecked(true);
                break;
            case R.id.menu_blue:
                findViewById(R.id.menu_activity).setBackgroundColor(Color.BLUE);
                item.setChecked(true);
                break;
        }

        return true;
    }

    // Popup Menu
    public void showPlanets(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_planets, popupMenu.getMenu());
        popupMenu.show();
    }
}
