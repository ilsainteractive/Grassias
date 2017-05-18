package com.ilsa.grassis.activites;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;
import com.ilsa.grassis.R;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeBitmapScanner;
import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

import static com.ilsa.grassis.R2.id.barcode;

public class CodeScanner extends AppCompatActivity implements BarcodeRetriever {
    private static final String TAG = "BarcodeMain";

    CheckBox fromXMl;
    SwitchCompat drawRect, autoFocus, supportMultiple, touchBack, drawText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_scanner);

        final BarcodeCapture barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(barcode);
        barcodeCapture.setRetrieval(this);

        barcodeCapture.setShowDrawRect(true);

        fromXMl = (CheckBox) findViewById(R.id.from_xml);
        drawRect = (SwitchCompat) findViewById(R.id.draw_rect);
        autoFocus = (SwitchCompat) findViewById(R.id.focus);
        supportMultiple = (SwitchCompat) findViewById(R.id.support_multiple);
        touchBack = (SwitchCompat) findViewById(R.id.touch_callback);
        drawText = (SwitchCompat) findViewById(R.id.draw_text);

        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromXMl.isChecked()) {

                } else {
                    barcodeCapture.setShowDrawRect(drawRect.isChecked());
                    barcodeCapture.setSupportMultipleScan(supportMultiple.isChecked());
                    barcodeCapture.setTouchAsCallback(touchBack.isChecked());
                    barcodeCapture.shouldAutoFocus(autoFocus.isChecked());
                    barcodeCapture.setShouldShowText(drawText.isChecked());
                    barcodeCapture.refresh();
                }
            }
        });
    }

    @Override
    public void onRetrieved(final Barcode barcode) {
        Log.d(TAG, "Barcode read: " + barcode.displayValue);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(CodeScanner.this)
                        .setTitle("code retrieved")
                        .setMessage(barcode.displayValue);
                builder.show();
            }
        });
    }

    @Override
    public void onRetrievedMultiple(final Barcode closetToClick, final List<BarcodeGraphic> barcodeGraphics) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String message = "Code selected : " + closetToClick.displayValue + "\n\nother " +
                        "codes in frame include : \n";
                for (int index = 0; index < barcodeGraphics.size(); index++) {
                    Barcode barcode = barcodeGraphics.get(index).getBarcode();
                    message += (index + 1) + ". " + barcode.displayValue + "\n";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(CodeScanner.this)
                        .setTitle("code retrieved")
                        .setMessage(message);
                builder.show();
            }
        });

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onRetrievedFailed(String reason) {

    }


}
