package com.example.almasud.fundamental.content_provider;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.almasud.fundamental.MainActivity;
import com.example.almasud.fundamental.R;

import java.util.ArrayList;

public class ContentProviderActivity extends AppCompatActivity {
    Button view, add, modify, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
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

        view = findViewById(R.id.view_contacts_btn);
        add = findViewById(R.id.add_contact_btn);
        modify = findViewById(R.id.modify_contact_btn);
        delete = findViewById(R.id.delete_contact_btn);

        }

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation why need permission.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_CONTACTS)) {

                String message = "App permissions is needed to Read and Write Contacts.";
                MainActivity.messageDialog(this, "Alert", message, "We hope you understand.");

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_CONTACTS,
                        },
                        1);
                // requestCode is app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayContacts();
                    Log.i("MainActivity", "Contact list displayed.");
                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createContact("Mr. X", "0123456789");
                    Log.i("MainActivity", "Contact is added.");
                }
            });

            modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateContact("Mr. X", "987654321");
                    Log.i("MainActivity", "Contact is modified.");
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteContact("Mr. X");
                    Log.i("MainActivity", "Display contact is deleted.");
                }
            });
        }
    }

    // Permission callback method
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            MainActivity.messageDialog(this,"Success", "Your permission is successfully granted.\nNow you can show your current location.", null);
        }
    }

    // Display contacts
    private void displayContacts() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        if(cur != null) {
            if(cur.getCount() > 0) {
                while(cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if(Integer.parseInt(cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER ))) > 0) {
                        Cursor phCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.
                                        Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                        while(phCur.moveToNext()) {
                            String phoneNo = phCur.getString(phCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Toast.makeText(this, "Name: " + name + " Phone: " + phoneNo,
                                    Toast.LENGTH_LONG).show();
                        }
                        phCur.close();
                    }
                }
            }
            cur.close();
        }
    }

    // Create contact
    private void createContact(String name, String phoneNo) {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        if(cur != null) {
            if(cur.getCount() > 0) {
                while(cur.moveToNext()) {
                    String existsName = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));
                    if(existsName.contains(name)) {
                        Toast.makeText(this, "The contact name " + name +
                                " is already exists!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
            cur.close();
        }

        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        operations.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, "Cloud")
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "tutorial.arm@gmail.com")
                .build());
        operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract
                        .CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                .build());
        operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract
                        .CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNo)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                .build());

        try {
            cr.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "New contact Name: " + name + " and Phone Number: "
                + phoneNo + " is created.", Toast.LENGTH_LONG).show();
    }

    // Update contact
    private void updateContact(String name, String phoneNo) {
        ContentResolver cr = getContentResolver();

        String where = ContactsContract.Data.DISPLAY_NAME + " = ? AND "
                + ContactsContract.Data.MIMETYPE + " = ? AND "
                + ContactsContract.CommonDataKinds.Phone.TYPE + " = ?";
        String[] params = new String[] {name,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME)};

        Cursor phoneCur = cr.query(ContactsContract.Data.CONTENT_URI, null,
                where, params, null);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        if(phoneCur == null) {
            createContact(name, phoneNo);
        } else {
            operations.add(ContentProviderOperation.newUpdate(
                    ContactsContract.Data.CONTENT_URI).withSelection(where, params)
                    .withValue(ContactsContract.CommonDataKinds.Phone.DATA, phoneNo)
                    .build());
            phoneCur.close();

            try {
                cr.applyBatch(ContactsContract.AUTHORITY, operations);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (OperationApplicationException e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(this, "The contact Name: " + name + " is updated.",
                Toast.LENGTH_LONG).show();
    }

    // Delete Contact
    private void deleteContact(String name) {
        ContentResolver cr = getContentResolver();

        String where = ContactsContract.Data.DISPLAY_NAME + " = ?";
        String[] params = new String[]{name};

        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        operations.add(ContentProviderOperation.newDelete(
                ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(where, params).build());
        try {
            cr.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "The contact Name: " + name + " is deleted.",
                Toast.LENGTH_LONG).show();
    }

}
