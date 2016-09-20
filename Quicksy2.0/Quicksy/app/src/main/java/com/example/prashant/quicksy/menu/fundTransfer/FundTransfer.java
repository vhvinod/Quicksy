package com.example.prashant.quicksy.menu.fundTransfer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prashant.quicksy.R;
import com.example.prashant.quicksy.menu.MenuActivity;

public class FundTransfer extends AppCompatActivity implements View.OnClickListener {

    Button readQr, writeQr;//, goBack;
    private static final int ACTIVITY_RESULT_QR_DRDROID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer);

        readQr = (Button)findViewById(R.id.readQr);
        writeQr = (Button)findViewById(R.id.writeQr);
        //goBack = (Button)findViewById(R.id.goBack);

        readQr.setOnClickListener(this);
        writeQr.setOnClickListener(this);
        //goBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.readQr:
                startActivity(new Intent(this, QR_Scan.class));
                break;

            case R.id.writeQr:
                startActivity(new Intent(this, QR_Generate.class));
                break;

            case R.id.goBack:
                startActivity(new Intent(this, MenuActivity.class));
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(ACTIVITY_RESULT_QR_DRDROID == requestCode
                && data != null && data.getExtras() != null ) {

            ImageView imgResult = ( ImageView ) findViewById(R.id.img_result);

            String qrCode = data.getExtras().getString("la.droid.qr.result");

            if(qrCode == null || qrCode.trim().length() == 0) {

                Toast.makeText(getBaseContext(), "QR Code Image " +
                        "is not Saved", Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(getBaseContext(), "QR Code Image is Saved"
                    + " " + qrCode, Toast.LENGTH_LONG).show();

            imgResult.setImageURI( Uri.parse(qrCode) );

            imgResult.setVisibility( View.VISIBLE );
        }
    }
}
