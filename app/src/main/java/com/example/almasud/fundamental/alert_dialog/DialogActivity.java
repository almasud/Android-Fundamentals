package com.example.almasud.fundamental.alert_dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.almasud.fundamental.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

    Button alertBtn1, alertBtn2, alertBtn3, listBtn, checkBtn, radioBtn, customDialogBtn;
    EditText dateText, timeText;
    DatePickerDialog datePicker;
    TimePickerDialog timePicker;
    String selectedItem = "";
    Calendar calendar;
    int day, month, year, hour, minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);

        // Initialize resources.
        alertBtn1 = findViewById(R.id.button_alert_1);
        alertBtn2 = findViewById(R.id.button_alert_2);
        alertBtn3 = findViewById(R.id.button_alert_3);
        listBtn = findViewById(R.id.button_list);
        checkBtn = findViewById(R.id.button_checkbox);
        radioBtn = findViewById(R.id.button_radio);
        dateText = findViewById(R.id.edit_date);
        timeText = findViewById(R.id.edit_time);
        customDialogBtn = findViewById(R.id.button_custom_dialog);

        // Value initialization
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        minute = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);


        // Click action for all buttons refer to this object.
        alertBtn1.setOnClickListener(this);
        alertBtn2.setOnClickListener(this);
        alertBtn3.setOnClickListener(this);
        listBtn.setOnClickListener(this);
        checkBtn.setOnClickListener(this);
        radioBtn.setOnClickListener(this);
        dateText.setOnClickListener(this);
        timeText.setOnClickListener(this);
        customDialogBtn.setOnClickListener(this);

        // Set date after pick by DatePickerDialog
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Set pick date in calendar
                calendar.set(year, month, dayOfMonth);
                // Set date in edit text from calendar
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
                ((EditText) findViewById(R.id.edit_date)).setText(dateFormat.format(calendar.getTime()));
            }
        };

        // Pick a date with DatePickerDialog
        datePicker = new DatePickerDialog(this, dateSetListener, year, month, day);

        // Set time after pick by TimePickerDialog
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Set time in calendar
                calendar.set(0, 0, 0, hourOfDay, minute);
                // Set time in edit text from calendar.
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                ((EditText) findViewById(R.id.edit_time)).setText(timeFormat.format(calendar.getTime()));
            }
        };

        // Pick a time with TimePickerDialog
        timePicker = new TimePickerDialog(this, timeSetListener, hour, minute, false);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_alert_1) {
            alertOne();
        } else if (v.getId() == R.id.button_alert_2) {
            alertTwo();
        } else if (v.getId() == R.id.button_alert_3) {
            alertThree();
        } else if (v.getId() == R.id.button_list) {
            alertList();
        } else if (v.getId() == R.id.button_checkbox) {
            checkboxDialog();
        } else if (v.getId() == R.id.button_radio) {
            radioDialog();
        } else if (v.getId() == R.id.edit_date) {
            datePicker.show();
        }else if (v.getId() == R.id.edit_time) {
            timePicker.show();
        } else if (v.getId() == R.id.button_custom_dialog) {
            customDialog();
        }
    }

    // Alert dialog with positive button
    public void alertOne() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
        builder.setTitle("Alert Dialog One");
        builder.setMessage("Alert Dialog Message");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("Alert Dialog One OK Pressed.");
                    }
                });
        builder.show();
    }

    // Alert dialog with positive and negative button.
    public void alertTwo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
        builder.setTitle("Alert Dialog Two");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to an action?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("Action is triggered.");
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("You Pressed Cancel.");
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    // Alert dialog with positive, negative and neutral button.
    public void alertThree() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
        builder.setTitle("Alert Dialog Three");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("This alert dialog cannot cancel outside click.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("You Pressed OK.");
                    }
                });
        builder.setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("You Pressed Neutral.");
                    }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("You Pressed Cancel.");
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    // Alert dialog with a simple list view
    public void alertList() {
        final CharSequence[] items = {"Samsung", "Xiaomi", "Huawei", "NOKIA", "Symphony"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("You Pressed: "+ items[which]);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    // Alert dialog with checkbox.
    public void checkboxDialog() {
        final ArrayList<String> selectedItems = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
        builder.setTitle("Select your choose");
        builder.setMultiChoiceItems(R.array.arrayCountry, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                List<String> items = Arrays.asList(getResources().getStringArray(R.array.arrayCountry));
                if (isChecked) {
                    selectedItems.add(items.get(which));
                } else if (selectedItems.contains(items.get(which))) {
                    selectedItems.remove(items.get(which));
                }
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            String selectedIndex = "";
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(String item : selectedItems) {
                    if (item.equals(selectedItems.get(selectedItems.size() - 1))) {
                        selectedIndex += item;
                    } else {
                        selectedIndex += item + ", ";
                    }
                }
                showToast("You are selected: " + selectedIndex + ".");
            }
        });
        builder.show();
    }

    // Alert dialog radio button
    public void radioDialog() {
        final String[] items = getResources().getStringArray(R.array.arrayCountry);
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
        builder.setTitle("Select your choose");
        builder.setSingleChoiceItems(R.array.arrayCountry, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                selectedItem += items[which];
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("You select: " + selectedItem);
            }
        });
        builder.show();
    }

    // Custom Dialog
    public void customDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = DialogActivity.this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.login_dialog, null));
        builder.setPositiveButton(R.string.sign_in, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User sign in...
                showToast("You pressed sign in button.");
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("You pressed cancel button.");
                dialog.cancel();
            }
        });

        builder.show();
    }

    // Show Toast Message
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}
