package com.traviswu.payme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by traviswu on 2015-05-30.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button sharksplit = (Button) findViewById(R.id.sharkSplit);
            sharksplit.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v){
                    startActivity(new Intent(MainActivity.this, SharkSplit.class ));
                }
            });

    }
}