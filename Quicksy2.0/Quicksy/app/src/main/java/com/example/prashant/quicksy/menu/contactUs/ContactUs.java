    package com.example.prashant.quicksy.menu.contactUs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.prashant.quicksy.menu.MenuActivity;
import com.example.prashant.quicksy.R;
import android.support.v7.app.AppCompatActivity;


public class ContactUs extends AppCompatActivity implements View.OnClickListener{

    //Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        //goBack = (Button)findViewById(R.id.goBack);
        //goBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.goBack:
                startActivity(new Intent(this, MenuActivity.class));
                break;
        }
    }

}
