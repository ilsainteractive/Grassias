package com.ilsa.grassis.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ilsa.grassis.library.Constants;

/**
 * Created by Ilsa on 1/4/2017.
 */

public class Dailogs {

    public static void ShowToast(Context context, String message, int type) {
        if (Constants.LONG_DAILOG == type)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        else if (Constants.SHORT_DAILOG == type)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static Dialog CreateBottomSheet(Context context, int style, int layout, boolean cancelable) {
        if (context != null) {
            Dialog mBottomSheetDialog = new Dialog(context, style);
            mBottomSheetDialog.setContentView(layout); // your custom view.
            mBottomSheetDialog.setCancelable(cancelable);
            mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
            return mBottomSheetDialog;
        } else {
            return null;
        }
    }
}