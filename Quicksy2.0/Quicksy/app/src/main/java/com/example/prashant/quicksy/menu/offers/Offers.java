package com.example.prashant.quicksy.menu.offers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.prashant.quicksy.R;
import com.example.prashant.quicksy.menu.MenuActivity;

public class Offers extends AppCompatActivity implements View.OnClickListener{

//    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);

// This is just an example. You can use whatever collection of images.
        int[] mResources = {
                R.drawable.offer1,
                R.drawable.offer2,
                R.drawable.offer3,
                R.drawable.offer4
        };

        //goBack = (Button)findViewById(R.id.goBack);
        //goBack.setOnClickListener(this);

        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(this, mResources);

        mViewPager.setAdapter(mCustomPagerAdapter);
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
