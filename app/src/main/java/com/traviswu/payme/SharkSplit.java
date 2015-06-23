package com.traviswu.payme;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;


public class SharkSplit extends Activity {
    public static final String TOTAL_AMOUNT = "com.traviswu.payme.total_amount";
    public static final String CONTACT_LIST = "com.traviswu.payme.contact_list";
    private static final int CONTACT_PICKER_RESULT = 1001;
    private static String DEBUG_TAG = "From LaunchContact";
    private final int ARRAY_INDEX_NAME = 0;
    private final int ARRAY_INDEX_PHONE = 1;

    String[] info = new String[2];
    ArrayList<String> fini = new ArrayList<String>();

    private Button split_button;
    private EditText dollar_amount;
    //initialize upon creation of the class
    public SharkSplit() {
        DEBUG_TAG = getClass().getName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shark_split);

        init();
    }

    public void init() {
        split_button = (Button) findViewById(R.id.split_button);
        split_button.setEnabled(false);  //disable continue to split button at first

        dollar_amount = (EditText) findViewById(R.id.amount_to_split);
        dollar_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((dollar_amount.getText().length() != 0) && (Float.parseFloat(dollar_amount.getText().toString()) > 0))
                    split_button.setEnabled(true);
                else
                    split_button.setEnabled(false);
            }
        });  //enable continue to split button after it can be proven that a number > 0 has been entered.

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

                            name = people.getString(indexName);
                            number = people.getString(indexNum);

                            //handle data input
                            if (!fini.contains(name)) {
                                info[ARRAY_INDEX_NAME] = name;
                                fini.add(name);
                                info[ARRAY_INDEX_PHONE] = number;
                                fini.add(number);
                            }

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
                        addNewRow();
                        info = new String[2];
                    }
                    break;
            }
        } else {
            //wait to handle failure
            Log.w(DEBUG_TAG, "Warning: activity result not ok");
        }
    }

    private void addNewRow() {
        TableLayout myTable = (TableLayout) findViewById(R.id.people_list);

        final TableRow newRow = new TableRow(this);

        final TextView nameText = new TextView(this);
        nameText.setText(info[0]);
        final TextView phoneText = new TextView(this);
        phoneText.setText(info[1]);
        Button deleteThisContact = new Button(this);
        deleteThisContact.setText("X");
        deleteThisContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iterator<String> ite = fini.iterator();
                while (ite.hasNext()) {
                    String current = ite.next();
                    if (current.equals(nameText.getText().toString()) || current.equals(phoneText.getText().toString()))
                        ite.remove();
                }
                ((ViewManager) newRow.getParent()).removeView(newRow);
            }
        });

        newRow.addView(nameText);
        newRow.addView(phoneText);
        newRow.addView(deleteThisContact);
        myTable.addView(newRow);
        // wait to see if i need to do more with setContentView and etc
    }

//    private void displayTable() {
//        final PopupWindow choose = new PopupWindow();
//        TableLayout myTable = new TableLayout(this);
//        RelativeLayout main = (RelativeLayout) findViewById(R.id.shark_split_main);
//        myTable.removeAllViews();
//
//        Button dismiss = new Button(this);
//        dismiss.setText("Done");
//        dismiss.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                choose.dismiss();
//            }
//        });
//
//        choose.showAtLocation(main, Gravity.CENTER, 10, 10);
//        choose.update(50, 50, 300, 80);
//        choose.setContentView(myTable);
//
//
//        String[] infoArray = new String[info.size()];
//        info.toArray(infoArray);
//
//        for (int i = 0; i < infoArray.length; i += 2) {
//            final TableRow newRow = new TableRow(this);
//
//            TextView tvNew1 = new TextView(this);
//            tvNew1.setText(infoArray[i]);
//            tvNew1.setId(R.id.RowText1);
//            newRow.addView(tvNew1);
//
//            TextView tvNew2 = new TextView(this);
//            tvNew2.setText(infoArray[i + 1]);
//            tvNew1.setId(R.id.RowText2);
//            newRow.addView(tvNew2);
//
//            newRow.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    TextView name = (TextView) newRow.getChildAt(0);
//                    //name.setText("clicked");
//
//                    TextView phone = (TextView) newRow.getChildAt(1);
//                    //phone.setText("num here!!");
//                    //TextView phone_num = (TextView)newRow.getChildAt(2);
//                    if (!fini.contains(name.getText().toString())) {
//                        fini.add(name.getText().toString());
//                        fini.add(phone.getText().toString());
//
//                    }
//                }
//            });
//            myTable.addView(newRow);
//        }
//        myTable.addView(dismiss);
//        myTable.setBackgroundColor(0x00000000); //change pop up color
//        choose.setContentView(myTable);
//        //main.addView(myTable);
//        setContentView(main);
//    }
}



