package com.traviswu.payme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.util.Log;

import org.w3c.dom.Text;


public class ContinueToSplit extends ActionBarActivity {
    double total_amount;
    int n_people;
    int[] shares;
    double[] subtotal;
    int total_share = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent newIntent = getIntent();
        total_amount = newIntent.getDoubleExtra(SharkSplit.TOTAL_AMOUNT, 0.0);
        n_people = newIntent.getIntExtra(SharkSplit.N_PEOPLE, 1);

        shares = new int [n_people];
        subtotal = new double [n_people];
        /**
        TextView newTextView = new TextView(this);
        newTextView.setTextSize(40);
        String newMessage = "Total money $"+ total_amount+" between " +n_people+ " people.";
        newTextView.setText(newMessage);
        setContentView(newTextView);
        **/

        setContentView(R.layout.continue_to_split);
        init();
    }

    public void init(){
        TableLayout myTable = (TableLayout)findViewById(R.id.table_of_shares);


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
            tvNew1.setText("Person " + i);
            newRow.addView(tvNew1);

            final EditText tvNew2 = new EditText(this);
            tvNew2.setHint("has "+shares[i]+" shares");

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
                }
            });
            newRow.addView(tvNew2);

            TextView tvNew3 = new TextView(this);
            tvNew3.setText("Comes up to $"+subtotal[i]);
            newRow.addView(tvNew3);

            myTable.addView(newRow);
        }
        TableRow row2 = new TableRow(this);
        //Button even_split = new Button();


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

//    public void checkOut(View view){
//        Intent newIntent = new Intent (ContinueToSplit.class,ProceedToCheckout.class);
//
//        int total_share = 0;
//        for (int i =0; i<n_people; i++){
//            total_share += shares[i];
//        }
//
//        double money_per_share = total_amount/total_share;
//        for (int i =0; i<n_people; i++){
//            subtotal[i] = shares[i]*money_per_share;
//        }
//
//
//    }
}
