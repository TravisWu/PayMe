package com.traviswu.payme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class ContinueToSplit extends ActionBarActivity {
    double total_amount;
    int n_people;
    int[] shares = new int [n_people];
    double[] subtotal = new double [n_people];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent newIntent = getIntent();
        total_amount = newIntent.getDoubleExtra(SharkSplit.TOTAL_AMOUNT, 0.0);
        n_people = newIntent.getIntExtra(SharkSplit.N_PEOPLE, 1);


        TextView newTextView = new TextView(this);
        newTextView.setTextSize(40);
        String newMessage = "Total money $"+ total_amount+" between " +n_people+ " people.";
        newTextView.setText(newMessage);
        setContentView(newTextView);


//        final EditText[] people_list = new EditText[n_people];
//        final TextView[] people_each_own = new TextView[n_people];
//        for (int i=0; i < n_people; i++){
//            people_list[i].setTextSize(40);
//            people_list[i].setText(shares[i] + "");
//            people_list[i].addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    int number = Integer.parseInt(s.toString());
//                    people_list[i] = number;
//                }
//            });
//
//            people_each_own[i].setTextSize(40);
//            people_each_own[i].setText(subtotal[i] + "");
//
//            setContentView(people_list[i]);
//            setContentView(people_each_own[i]);
//        }

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
        double shares = total_amount/n_people;
        for (int i=0; i<n_people; i++){
            this.shares[i]=1;
            subtotal[i]=shares;
        }
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
