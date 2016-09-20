package com.example.prashant.quicksy.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prashant.quicksy.R;
import com.example.prashant.quicksy.ServiceApiCall.ServiceHandler;
import com.example.prashant.quicksy.login.LoginActivity;
import com.example.prashant.quicksy.menu.contactUs.ContactUs;
import com.example.prashant.quicksy.menu.fundTransfer.FundTransfer;
import com.example.prashant.quicksy.menu.locator.Locator;
import com.example.prashant.quicksy.menu.offers.Offers;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends Activity {

    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    CustomGrid customGridAdapter;
    //Button bLogout;
    ImageButton fund_transfer, bank_balance;
    TextView acbalance;
    GridView grid;
    String[] web = {
            "Fund Transfer",
            "Locate Branch",
            "Offers",
            "Contact Us"
    };
    int[] imageId = {
            R.drawable.fund_transfer,
            R.drawable.branch_locator,
            R.drawable.offers,
            R.drawable.contact_us
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        CustomGrid adapter = new CustomGrid(MenuActivity.this, web, imageId);
        acbalance = (TextView) findViewById(R.id.acbalance);
        getAccounts();
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                onClick(view, position);
            }
        });
    }

    public void onClick(View v, int position) {
        Log.d("Response: ", "> " + v.getId());

        switch (position) {
            case 0:
                startActivity(new Intent(this, FundTransfer.class));
                break;

            case 1:
                startActivity(new Intent(this, Locator.class));
                break;

            case 2:
                startActivity(new Intent(this, Offers.class));
                break;

            case 3:
                startActivity(new Intent(this, ContactUs.class));
                break;

        }
    }
    public void getAccounts(){
        ServiceHandler sh = new ServiceHandler();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("client_id", "vh.vinod@gmail.com"));
        params.add(new BasicNameValuePair("token", "6dc65a60a214"));
        params.add(new BasicNameValuePair("accountno", "5555666677771100"));
        String jsonStr = String.valueOf(sh.makeServiceCall("http://retailbanking.mybluemix.net/banking/icicibank/balanceenquiry", ServiceHandler.GET, params));
        JSONArray jsonObj1;


        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {

                jsonObj1 = new JSONArray(jsonStr);
                JSONObject test =  new JSONObject(jsonObj1.getString(1));
                String cjeckk = String.valueOf(test.getString("balance"));
                acbalance.setText("Your Balance is: "+cjeckk);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
    }

}
