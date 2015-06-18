package com.traviswu.payme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class ContinueToSplit extends ActionBarActivity {
    final static String SUBTOTAL = "com.traviswu.payme.subtotal";
    double total_amount;
    int n_people;
    int[] shares;
    double[] subtotal;
    int total_share = 0;
    String[] infoArray;
    String[] names;
    String[] phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent newIntent = getIntent();
        total_amount = newIntent.getDoubleExtra(SharkSplit.TOTAL_AMOUNT, 0.0);
        infoArray = newIntent.getStringArrayExtra(SharkSplit.CONTACT_LIST);
        n_people = infoArray.length / 2;
        //n_people = newIntent.getIntExtra(SharkSplit.N_PEOPLE, 1);
        Log.d("Debug Tag", "size of array " + infoArray.length);
        names = new String[n_people];
        phone_number = new String[n_people];

        for (int i = 0; i < infoArray.length; i += 2) {  //note here infoArray is 2x n_people
            names[i / 2] = infoArray[i];
            Log.d("Name Check", names[i / 2]);
            phone_number[i / 2] = infoArray[i + 1];
            Log.d("Phone Check", phone_number[i / 2]);
        }
        shares = new int [n_people];
        subtotal = new double [n_people];

        setContentView(R.layout.continue_to_split);
        init();
    }

    public void init(){
        TableLayout myTable = (TableLayout)findViewById(R.id.table_of_shares);
        myTable.removeAllViews();

        TableRow text = new TableRow(this);
        TextView newTextView = new TextView(this);
        String newMessage = "Split $" + total_amount + " between " + n_people + " people.";
        newTextView.setText(newMessage);
        text.addView(newTextView);
        myTable.addView(text);

        TableRow row0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText("Split");
        tv0.setTextColor(Color.GREEN);
        row0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText("Shares");
        tv1.setTextColor(Color.GREEN);
        row0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText("Subtotal");
        tv2.setTextColor(Color.GREEN);
        row0.addView(tv2);

        //Log.d("shares", shares.length+"");
        myTable.addView(row0);

        for(int i =0; i<shares.length; i++){
            TableRow newRow = new TableRow(this);
            TextView tvNew1 = new TextView(this);
            tvNew1.setText(names[i]);
            newRow.addView(tvNew1);

            final EditText tvNew2 = new EditText(this);
            tvNew2.setHint(shares[i]+"");
            tvNew2.setInputType(InputType.TYPE_CLASS_NUMBER);
            final int index = i;
            tvNew2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    total_share -= shares[index];
                    int newShare = Integer.parseInt(tvNew2.getText().toString());
                    shares[index]= newShare;
                    total_share += newShare;

                    double new_share_per_slice = total_amount/total_share;
                    for (int l=0; l <shares.length; l++){
                        subtotal[l] = shares[l] * new_share_per_slice;
                    }

                    init();
                }
            });
            newRow.addView(tvNew2);

            TextView tvNew3 = new TextView(this);
            tvNew3.setText("$"+subtotal[i]);
            newRow.addView(tvNew3);

            myTable.addView(newRow);
        }
        TableRow row2 = new TableRow(this);
        final Button even_split = new Button(this);
        even_split.setText("Even Split");
        even_split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evenSplit(v);
            }
        });
        myTable.addView(even_split, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_continue_to_split, menu);
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

    //This method will change everyone share to be evenly distributed.
    //It will not direct user to Checkout menu
    //It can serve as a mid-point where users to choose to go to different variations from there

    public void evenSplit(View view){

        total_share = n_people;
        double shares = total_amount/n_people;
        for (int i=0; i<n_people; i++){
            this.shares[i]=1;
            subtotal[i]=shares;
        }
        init();
    }

    public void checkOut(View view) {
        Intent newIntent = new Intent (ContinueToSplit.this, ProceedToCheckout.class);
        newIntent.putExtra(SharkSplit.CONTACT_LIST, phone_number);
        newIntent.putExtra(SUBTOTAL,subtotal);
        startActivity(newIntent);
    }
}
