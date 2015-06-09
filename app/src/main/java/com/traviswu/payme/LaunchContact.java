package com.traviswu.payme;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Iterator;
import java.util.Set;


public class LaunchContact extends ActionBarActivity {

    private static final int CONTACT_PICKER_RESULT = 1001;
    private static final String DEBUG_TAG = "From LaunchContact";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_contact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launch_contact, menu);
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

    public void launchContactPicker(View view) {
        Intent contactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        contactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(contactIntent, CONTACT_PICKER_RESULT);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    //handle contact result
                    Bundle extras = data.getExtras();
                    Set keys = extras.keySet();
                    Iterator<String> iter = keys.iterator();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        Log.v(DEBUG_TAG, key + "[" + extras.get(key) + "]");
                    }
                    Uri result = data.getData();
                    Log.v(DEBUG_TAG, "got result: " + result.toString());

                    //grab the id from URI
                    String id = result.getLastPathSegment();

                    Uri newUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                    String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
                    Cursor people = getContentResolver().query(newUri, projection, null, null, null);

                    int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int indexNum = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                    people.moveToFirst();
                    String[] columns = people.getColumnNames();

                    break;

            }
        } else {
            //wait to handle failure
            Log.w(DEBUG_TAG, "Warning: activity result not ok");
        }
    }
}
