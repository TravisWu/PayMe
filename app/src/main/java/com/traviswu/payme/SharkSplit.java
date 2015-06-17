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
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


public class SharkSplit extends ActionBarActivity {
    public static final String TOTAL_AMOUNT = "com.traviswu.payme.total_amount";
    //public static final String N_PEOPLE ="com.traviswu.payme.n_shares";
    public static final String CONTACT_LIST = "com.traviswu.payme.contact_list";

    private static final int CONTACT_PICKER_RESULT = 1001;
    private static final String DEBUG_TAG = "From LaunchContact";
    ArrayList<String> info = new ArrayList<String>();
    ArrayList<String> fini = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shark_split);
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

    public void continueToSplit(View view){
        Intent newIntent = new Intent (SharkSplit.this, ContinueToSplit.class);
        EditText totalAmount = (EditText) findViewById(R.id.amount_to_split);
        //EditText nPeople = (EditText) findViewById(R.id.n_people);
        Log.d(DEBUG_TAG, "size of " + fini.size());
        String[] finiArray = fini.toArray(new String[fini.size()]);

        if (!totalAmount.getText().toString().equals("")) //guard
            newIntent.putExtra(TOTAL_AMOUNT, Double.parseDouble(totalAmount.getText().toString()));
        else
            newIntent.putExtra(TOTAL_AMOUNT, 0);
        //newIntent.putExtra(N_PEOPLE, Integer.parseInt(nPeople.getText().toString()));
        newIntent.putExtra(CONTACT_LIST, finiArray);
        startActivity(newIntent);
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
                    Cursor people = null;
                    String name;
                    String number;
                    try {
                        Uri result = data.getData();
                        Log.v(DEBUG_TAG, "got result: " + result.toString());

                        //grab the id from URI
                        //String id = result.getLastPathSegment();
                        Uri newUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
                        people = getContentResolver().query(newUri, projection, null, null, null);


                        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        int indexNum = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);


                        if (people.moveToFirst()) {
                            do {
                                //while (!people.isAfterLast()){
                                name = people.getString(indexName);
                                number = people.getString(indexNum);

                                //handle data input
                                if (!info.contains(name)) {
                                    info.add(name);
                                    info.add(number);
                                }
                            } while (people.moveToNext());
                            //}
                        } else {
                            Log.w(DEBUG_TAG, "No results");
                        }

                    } catch (Exception e) {
                        Log.e(DEBUG_TAG, "Failed to retrieve contact", e);
                    } finally {
                        if (people != null)
                            people.close();

                        //handle display if any
                        //could start by display a table of all ppl and their number

                        //displayTable();
                    }
                    break;
            }
        } else {
            //wait to handle failure
            Log.w(DEBUG_TAG, "Warning: activity result not ok");
        }
    }

    private void displayTable() {
        TableLayout myTable = (TableLayout) findViewById(R.id.people_list);
        myTable.removeAllViews();

        String[] infoArray = new String[info.size()];
        info.toArray(infoArray);

        for (int i = 0; i < infoArray.length; i += 2) {
            final TableRow newRow = new TableRow(this);

            TextView tvNew1 = new TextView(this);
            tvNew1.setText(infoArray[i]);
            tvNew1.setId(R.id.RowText1);
            newRow.addView(tvNew1);

            TextView tvNew2 = new TextView(this);
            tvNew2.setText(infoArray[i + 1]);
            tvNew1.setId(R.id.RowText2);
            newRow.addView(tvNew2);

            newRow.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    TextView name = (TextView) newRow.getChildAt(0);
                    //name.setText("clicked");

                    TextView phone = (TextView) newRow.getChildAt(1);
                    //phone.setText("num here!!");
                    //TextView phone_num = (TextView)newRow.getChildAt(2);
                    if (!fini.contains(name.getText().toString())) {
                        fini.add(name.getText().toString());
                        fini.add(phone.getText().toString());

                    }
                }
            });
            myTable.addView(newRow);
        }
    }
}


