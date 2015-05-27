package com.traviswu.payme;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ContinueToSplit extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent newIntent = getIntent();
        double money_per_slice = newIntent.getDoubleExtra(MainActivity.MONEY_PER_SLICE, 0.0);
        int n_slices = newIntent.getIntExtra(MainActivity.N_SHARES, 0);

        TextView newTextView = new TextView(this);
        newTextView.setTextSize(40);
        String newMessage = "Each share is $" + money_per_slice+ " . Can distribute up to " + n_slices + " shares.";
        newTextView.setText(newMessage);

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
}
