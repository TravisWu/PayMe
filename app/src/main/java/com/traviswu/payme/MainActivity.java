package com.traviswu.payme;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    public static final String TOTAL_AMOUNT = "com.traviswu.payme.total_amount";
    public static final String N_PEOPLE ="com.traviswu.payme.n_shares";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Intent newIntent = new Intent (this, ContinueToSplit.class);
        EditText totalAmount = (EditText) findViewById(R.id.amount_to_split);
        EditText nPeople = (EditText) findViewById(R.id.n_people);

        newIntent.putExtra(TOTAL_AMOUNT, Integer.parseInt(totalAmount.getText().toString()));
        newIntent.putExtra(N_PEOPLE, Double.parseDouble(nPeople.getText().toString()));
        startActivity(newIntent);
    }

}
