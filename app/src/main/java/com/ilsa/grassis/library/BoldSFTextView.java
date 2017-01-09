package com.ilsa.grassis.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Ilsa on 1/4/2017.
 */

public class BoldSFTextView extends TextView {


    public BoldSFTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Heavy.otf");
        this.setTypeface(face);
    }

    public BoldSFTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Heavy.otf");
        this.setTypeface(face);
    }

    public BoldSFTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Heavy.otf");
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}