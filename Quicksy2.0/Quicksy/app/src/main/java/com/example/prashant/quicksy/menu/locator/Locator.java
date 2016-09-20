package com.example.prashant.quicksy.menu.locator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.prashant.quicksy.R;

public class Locator extends AppCompatActivity {

    private static final int ACTIVITY_RESULT_QR_DRDROID = 0;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locator);


        Intent i = new Intent();
        PackageManager manager = getPackageManager();
        i = manager.getLaunchIntentForPackage("com.androidexperiments.landmarker");
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(i);    }

	/*
	 * NOT USING FOR NOW ---- KEPT FOR FUTURE REFERENCE
	 * If we don't have QRDroid Application in our Device,
	 * It will call below method (qrDroidRequired)
	 *
	 */

    protected static void locatorRequired(final Locator activity) {
        // TODO Auto-generated method stub

        AlertDialog.Builder AlertBox = new AlertDialog.Builder(activity);

        AlertBox.setMessage("Locator application Missing");

        AlertBox.setPositiveButton("Direct Download", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

                activity.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://droid.la/apk/qr/")));
            }
        });

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
