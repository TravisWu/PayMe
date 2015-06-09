package com.traviswu.payme;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by traviswu on 2015-06-03.
 */
public class BloodyAct extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloodymarry);
    }

    private static final int CONTACT_PICKER_RESULT = 1001;

    public void doLaunchContactPicker(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    // handle contact results
                    Bundle extras = data.getExtras();
                    Set keys = extras.keySet();
                    Iterator iterate = keys.iterator();
//                    while (iterate.hasNext()) {
//                        String key = iterate.next();
//                        Log.v(DEBUG_TAG, key + "[" + extras.get(key) + "]");
//                    }
//                    Uri result = data.getData();
//                    Log.v(DEBUG_TAG, "Got a result: "
//                            + result.toString());
                    break;
            }

        } else {
            // gracefully handle failure LOL jkjk this is so not graceful but fuck it
            System.out.println("fuck you");
        }
    }

}