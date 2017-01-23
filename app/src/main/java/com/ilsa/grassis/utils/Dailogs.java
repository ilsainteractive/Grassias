package com.ilsa.grassis.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ilsa.grassis.library.Constants;

/**
 * The type Dailogs.
 */
public class Dailogs {

    /**
     * Show toast.
     *
     * @param context the context
     * @param message the message
     * @param type    the type
     */
    public static void ShowToast(Context context, String message, int type) {
        if (Constants.LONG_TIME == type)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        else if (Constants.SHORT_TIME == type)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Create bottom sheet dialog.
     *
     * @param context    the context
     * @param style      the style
     * @param layout     the layout
     * @param cancelable the cancelable
     * @return the dialog
     */
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