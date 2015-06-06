package com.traviswu.payme;

/**
 * Created by User on 02-06-2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by User on 01-06-2015.
 */
public class FragmentOne extends Fragment {
   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        return inflater.inflate(R.layout.main,container,false);
    }
} */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main,
                container, false);

        Button sharkSplit = (Button) view.findViewById(R.id.sharkSplit);
        sharkSplit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntend = new Intent(FragmentOne.this.getActivity(), SharkSplit.class);
                FragmentOne.this.startActivity(myIntend);
               /* startActivity(new Intent(FragmentOne.this, SharkSplit.class));*/
            }
        });

        Button buttonBlood = (Button) view.findViewById(R.id.buttonBlood);
        buttonBlood.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntend2 = new Intent(FragmentOne.this.getActivity(), BloodyAct.class);
                FragmentOne.this.startActivity(myIntend2);
               /* startActivity(new Intent(FragmentOne.this, BloodyAct.class));*/
            }
        });

//        Button sharkSplit = (Button) view.findViewById(R.id.sharkSplit);
//        sharkSplit.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntend = new Intent(FragmentOne.this.getActivity(), SharkSplit.class);
//                FragmentOne.this.startActivity(myIntend);
//               /* startActivity(new Intent(FragmentOne.this, SharkSplit.class));*/
//            }
//        });
        return view;
    }
}