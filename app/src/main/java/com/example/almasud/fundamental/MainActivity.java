package com.example.almasud.fundamental;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.almasud.fundamental.activity_cycle_and_intent.EmployeeBasicActivity;
import com.example.almasud.fundamental.alert_dialog.DialogActivity;
import com.example.almasud.fundamental.broadcast_receiver.BroadcastReceiverActivity;
import com.example.almasud.fundamental.camera_app.CameraAppActivity;
import com.example.almasud.fundamental.content_provider.ContentProviderActivity;
import com.example.almasud.fundamental.event_handling.EventHandlingActivity;
import com.example.almasud.fundamental.firebase_service.FirebaseActivity;
import com.example.almasud.fundamental.checkbox_radio_button_spinner.CheckboxRadioButtonSpinnerActivity;
import com.example.almasud.fundamental.fragment.FragmentActivity;
import com.example.almasud.fundamental.list_and_recycler_view.ListAndRecyclerViewActivity;
import com.example.almasud.fundamental.map_location.MapActivity;
import com.example.almasud.fundamental.menu.MenuActivity;
import com.example.almasud.fundamental.navigation.TabLayoutViewPagerActivity;
import com.example.almasud.fundamental.processes_and_threads.ProcessThreadActivity;
import com.example.almasud.fundamental.http_api.HttpApiActivity;
import com.example.almasud.fundamental.searchable_interface.SearchableInterfaceActivity;
import com.example.almasud.fundamental.service.ServiceActivity;
import com.example.almasud.fundamental.shared_preference.SharedPreferenceActivity;
import com.example.almasud.fundamental.sqlite_database.SQLiteDBActivity;

/**
 * This activity is the launcher activity.
 * @author: Abdullah Almasud.
 */
public class MainActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Activity Life Cycle Activity
    public void activityLifeCycle(View view) {
        intent = new Intent(MainActivity.this, EmployeeBasicActivity.class);
        startActivity(intent);
    }

    // Checkbox Radio Button and Spinner Activity
    public void activityCheckboxRadioButtonSpinner(View view) {
        intent = new Intent(MainActivity.this, CheckboxRadioButtonSpinnerActivity.class);
        startActivity(intent);
    }

    // Event Handling Activity
    public void activityEventHandle(View view) {
        intent = new Intent(MainActivity.this, EventHandlingActivity.class);
        startActivity(intent);
    }

    // Menu activity
    public void activityMenu(View view) {
        intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    // Alert Dialog Activity
    public void activityAlertDialog(View view) {
        intent = new Intent(MainActivity.this, DialogActivity.class);
        startActivity(intent);
    }

    // List and Recycle view with custom adapter
    public void activityListView(View view) {
        intent = new Intent(MainActivity.this, ListAndRecyclerViewActivity.class);
        startActivity(intent);
    }

    // Shared Preference Activity
    public void activitySharedPreference(View view) {
        intent = new Intent(MainActivity.this, SharedPreferenceActivity.class);
        startActivity(intent);
    }

    // Fragment
    public void activityFragment(View view) {
        intent = new Intent(MainActivity.this, FragmentActivity.class);
        startActivity(intent);
    }

    // SQLite Activity
    public void activitySQLiteDB(View view) {
        intent = new Intent(MainActivity.this, SQLiteDBActivity.class);
        startActivity(intent);
    }

    // Process and Thread Activity
    public void activityProcessThread(View view) {
        intent = new Intent(MainActivity.this, ProcessThreadActivity.class);
        startActivity(intent);
    }

    // HTTP API Activity
    public void activityHttpApi(View view) {
        intent = new Intent(MainActivity.this, HttpApiActivity.class);
        startActivity(intent);
    }

    // Google Map and Location
    public void activityMapAndLocation(View view) {
        intent = new Intent(MainActivity.this, MapActivity.class);
        startActivity(intent);
    }

    // Layout with View Pager
    public void activityLayoutViewPager(View view) {
        startActivity(new Intent(MainActivity.this, TabLayoutViewPagerActivity.class));
    }




    // Broadcast Receiver Activity
    public void activityBroadCast(View view) {
        intent = new Intent(MainActivity.this, BroadcastReceiverActivity.class);
        startActivity(intent);
    }

    // Service Activity
    public void activityService(View view) {
        intent = new Intent(MainActivity.this, ServiceActivity.class);
        startActivity(intent);
    }

    // Content Provider Activity
    public void activityContentProvider(View view) {
        intent = new Intent(MainActivity.this, ContentProviderActivity.class);
        startActivity(intent);
    }

    // Firebase Cloud Messaging (FCM) Activity
    public void activityFCM(View view) {
        intent = new Intent(MainActivity.this, FirebaseActivity.class);
        startActivity(intent);
    }

    // Searchable Interface Activity
    public void activitySearchableInterface(View view) {
        startActivity(new Intent(this, SearchableInterfaceActivity.class));
    }

    // Camera application using file system Activity
    public void cameraAppActivity(View view) {
        startActivity(new Intent(this, CameraAppActivity.class));
    }

    // Show a dialog
    public static void messageDialog(final Context context, String title, String message, final String toastMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (toastMsg != null)
                    Toast.makeText(context, toastMsg, Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

}
