package com.example.almasud.fundamental.event_handling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.almasud.fundamental.R;

public class EventHandlingActivity extends AppCompatActivity implements View.OnClickListener {

    Button button1, button2, button3, button5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_handling);
        button1 = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);
        button3 = findViewById(R.id.btn3);
        button5 = findViewById(R.id.btn5);

        // Event handling by anonymous class.
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hello Android", Toast.LENGTH_LONG).show();
            }
        });
        // Event handling by implementing interface in this activity class.
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        // Event handling by another class (inner or outer) that implements
        // View.OnClickListener interface.
        button5.setOnClickListener(new Button5Action());
    }

    // Event handling by implementing interface in this activity class.
    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        String btnText = b.getText().toString();
        if (btnText.equals("Button-2"))
            Toast.makeText(this, "You pressed Button-2.", Toast.LENGTH_LONG).show();
        else if (btnText.equals("Button-3"))
            Toast.makeText(this, "You pressed Button-3.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "You pressed neither Button-2 nor Button-3.", Toast.LENGTH_LONG).show();
    }

    // Event handling by using custom method that called by onclick event from layout.
    public void btn4Action(View view) {
        Button b = (Button) view;
        String btnText = b.getText().toString();
        Toast.makeText(this, "You pressed " + btnText, Toast.LENGTH_LONG).show();
    }

    // Event handling by another class (inner or outer) that implements
    // View.OnClickListener interface.
    private class Button5Action implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String btnText = b.getText().toString();
            Toast.makeText(getApplicationContext(), "You pressed " + btnText, Toast.LENGTH_LONG).show();
        }
    }
}

