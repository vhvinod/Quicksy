package com.example.prashant.quicksy.menu.fundTransfer;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.prashant.quicksy.R;
import com.example.prashant.quicksy.ServiceApiCall.ServiceHandler;
import com.example.prashant.quicksy.menu.MenuActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QR_Scan extends AppCompatActivity implements View.OnClickListener{

    private static final int ACTIVITY_RESULT_QR_DRDROID = 0;
    TextView transferMessage, amountText, accountNoText, destAccountNo,acbalanceft;
    EditText amount;
    Button initiateTransfer, goBack, goHome;
    AlertDialog.Builder alertBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__scan);

        acbalanceft = (TextView) findViewById(R.id.acbalanceft);
        transferMessage = (TextView) findViewById(R.id.transferMessage);
        transferMessage.setVisibility(View.GONE);

        accountNoText= (TextView) findViewById(R.id.accountNoText);
        amountText = (TextView) findViewById(R.id.amountText);

        destAccountNo = (TextView) findViewById(R.id.destAccountNo);
        amount = (EditText) findViewById(R.id.amount);

        initiateTransfer= (Button) findViewById(R.id.initiateTransfer);
        goBack= (Button) findViewById(R.id.goBack);

        goHome= (Button) findViewById(R.id.goHome);

        Intent i = new Intent("la.droid.qr.scan");

        destAccountNo.setOnClickListener(QR_Scan.this);
        goBack.setOnClickListener(QR_Scan.this);
        goHome.setOnClickListener(QR_Scan.this);
        initiateTransfer.setOnClickListener(QR_Scan.this);
        alertBox = new AlertDialog.Builder(this);
        getAccounts();
        try {
            startActivityForResult(i, ACTIVITY_RESULT_QR_DRDROID);
        }
        catch (ActivityNotFoundException activity) {

            qrDroidRequired(QR_Scan.this);
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
                acbalanceft.setText("Your account balance is: "+cjeckk+" Rs.");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.goBack:
                startActivity(new Intent(this, FundTransfer.class));
                break;
            case R.id.initiateTransfer:
                initiateTransfer.setVisibility( View.GONE);
                destAccountNo.setVisibility(View.GONE);
                amount.setVisibility( View.GONE);
                amountText.setVisibility( View.GONE);
                accountNoText.setVisibility( View.GONE);
                transferMessage.setVisibility(View.VISIBLE);
                goBack.setVisibility(View.VISIBLE);
                goHome.setVisibility(View.VISIBLE);
                transferFund();
                break;
            case R.id.goHome:
                startActivity(new Intent(this, MenuActivity.class));
                break;
            case R.id.destAccountNo:
                if(destAccountNo.getText().toString() != ""){
                    startActivity(new Intent(this, QR_Scan.class));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if( ACTIVITY_RESULT_QR_DRDROID == requestCode
                && data != null && data.getExtras() != null ) {

            String result = data.getExtras().getString("la.droid.qr.result");

            destAccountNo.setText(result);
            destAccountNo.setVisibility(View.VISIBLE);
        }
    }

	/*
	 *
	 * If we don't have QRDroid Application in our Device,
	 * It will call below method (qrDroidRequired)
	 *
	 */

    protected static void qrDroidRequired(final QR_Scan activity) {
        // TODO Auto-generated method stub

        AlertDialog.Builder AlertBox = new AlertDialog.Builder(activity);

        AlertBox.setMessage("QRDroid Missing");

/*        AlertBox.setPositiveButton("Direct Download", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

                activity.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://droid.la/apk/qr/")));
            }
        });
*/
        AlertBox.setNeutralButton("From Market", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                activity.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://market.android.com/details?id=la.droid.qr")));
            }
        });

        AlertBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                dialog.cancel();
            }
        });

        AlertBox.create().show();
    }

    void transferFund(){
        ServiceHandler sh = new ServiceHandler();
        amount = (EditText) findViewById(R.id.amount);
        String destinationAccount = destAccountNo.getText().toString();
        transferMessage.setText("");

        if("5555666677771100".equals(destinationAccount)){
            alertBox.setMessage("Source and destination account number are same. Please go back and scan another QR code.");
            alertBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            alertBox.create().show();
        } else {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("client_id", "vh.vinod@gmail.com"));
            params.add(new BasicNameValuePair("token", "6dc65a60a214"));
            params.add(new BasicNameValuePair("srcAccount", "5555666677771100"));
            params.add(new BasicNameValuePair("destAccount", destinationAccount));
            params.add(new BasicNameValuePair("amt", amount.getText().toString()));
            params.add(new BasicNameValuePair("payeeDesc", "Test Transfer"));
            params.add(new BasicNameValuePair("payeeId", "1"));
            params.add(new BasicNameValuePair("type_of_transaction", "DTH"));
            String jsonStr = sh.makeServiceCall("http://retailbanking.mybluemix.net/banking/icicibank/fundTransfer", ServiceHandler.GET, params);
            transferMessage.setText(amount.getText() + " is transfered successfully to Account no. " + destAccountNo.getText());
        }
    }
}
