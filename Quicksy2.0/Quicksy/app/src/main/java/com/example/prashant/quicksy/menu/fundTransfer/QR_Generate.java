package com.example.prashant.quicksy.menu.fundTransfer;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prashant.quicksy.R;

public class QR_Generate extends AppCompatActivity implements View.OnClickListener{

    private static final int ACTIVITY_RESULT_QR_DRDROID = 0;
    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__generate);

        String code = "5555666677771100";
        Intent encode = new Intent("la.droid.qr.encode");
        encode.putExtra("la.droid.qr.code", code);
        encode.putExtra("la.droid.qr.image", true);
        encode.putExtra("la.droid.qr.size", 0);

        goBack= (Button) findViewById(R.id.goBack);
        goBack.setOnClickListener(QR_Generate.this);

        try {

            startActivityForResult(encode, ACTIVITY_RESULT_QR_DRDROID);
        }
        catch (ActivityNotFoundException activity) {

            qrDroidRequired(QR_Generate.this);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.goBack:
                startActivity(new Intent(this, FundTransfer.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(ACTIVITY_RESULT_QR_DRDROID == requestCode
                && data != null && data.getExtras() != null ) {

            ImageView imgResult = (ImageView) findViewById(R.id.img_result);

            String qrCode = data.getExtras().getString("la.droid.qr.result");

            if(qrCode == null || qrCode.trim().length() == 0) {

                Toast.makeText(getBaseContext(), "QR Code Image " +
                        "is not Saved", Toast.LENGTH_LONG).show();
                return;
            }

            //Toast.makeText(getBaseContext(), "QR Code Image is Saved"+ " " + qrCode, Toast.LENGTH_LONG).show();

            //imgResult.setImageURI(Uri.parse(qrCode));
            AlertDialog.Builder AlertBox = new AlertDialog.Builder(QR_Generate.this);

            String abc = String.valueOf(Uri.parse(qrCode));
            AlertBox.setMessage(abc);

            imgResult.setVisibility( View.VISIBLE );
        }
    }

	/*
	 *
	 * If we don't have QRDroid Application in our Device,
	 * It will call below method (qrDroidRequired)
	 *
	 */

    protected static void qrDroidRequired(final QR_Generate activity) {
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
}
