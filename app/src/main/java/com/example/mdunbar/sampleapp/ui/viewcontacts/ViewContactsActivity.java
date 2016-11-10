package com.example.mdunbar.sampleapp.ui.viewcontacts;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mdunbar.sampleapp.R;

public class ViewContactsActivity extends AppCompatActivity {


    private static final int READ_CONTACTS_PERM_REQUEST = 123;
    private TextView contactsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);

        contactsText = (TextView)findViewById(R.id.contacts_text);

        Button viewContactsButton = (Button) findViewById(R.id.view_contacts_action_button);
        viewContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadContacts();
            }
        });
    }

    private void loadContacts() {
        if (!readContactsPermGranted()) {
            if (shouldShowReadContactsPermRationale()) {
                showReadContactsPermRequestRationale();
            } else {
                requestReadContactsPerm();
            }
            return;
        }

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null,null, null, null);

        // Loop for every contact in the phone
        if (cursor != null && cursor.getCount() > 0) {
            String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
            StringBuffer output = new StringBuffer();

            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
                output.append("\n Display Name:" + name);
                output.append("\n");
            }

            contactsText.setText(output);
            cursor.close();
        }
    }

    private void showReadContactsPermRequestRationale() {
        Snackbar.make(findViewById(R.id.activity_landing_page),
                "To user this feature, you must grant permission to read your contacts",
                Snackbar.LENGTH_INDEFINITE).setAction("OK",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestReadContactsPerm();
                    }
                }).show();
    }

    private boolean shouldShowReadContactsPermRationale() {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS);
    }

    private boolean readContactsPermGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadContactsPerm() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERM_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_CONTACTS_PERM_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadContacts();
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
            }
        }
    }

}