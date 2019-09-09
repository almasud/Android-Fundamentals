package com.example.almasud.fundamental.list_and_recycler_view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.almasud.fundamental.R;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity implements ContactAdapter.ClickListener {
    EditText nameET, phoneET;
    Button addBtn;
    RecyclerView contactRV;
    Contact contact;
    ArrayList<Contact> contacts;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        nameET = findViewById(R.id.contactNameET);
        phoneET = findViewById(R.id.contactNoET);
        addBtn = findViewById(R.id.addListBtn);
        contactRV = findViewById(R.id.contactRV);

        contacts = new ArrayList<>();
        contactAdapter = new ContactAdapter(this, contacts, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        contactRV.setLayoutManager(layoutManager);
        contactRV.setAdapter(contactAdapter);
    }

    public void addContact(View view) {
        String contactName, contactNo;

        contactName = nameET.getText().toString();
        contactNo = phoneET.getText().toString();
        contact = new Contact(contactName, contactNo);
        contacts.add(contact);
        contactAdapter.updateData(contacts);

    }

    // Implement custom listener method
    @Override
    public void onClick(Contact contact) {
        Toast.makeText(this, contact.getName(), Toast.LENGTH_LONG).show();
    }
}
