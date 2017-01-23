package com.ilsa.grassis.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Medium TextView with font `SFUIText-Medium.otf`.
 */
public class MediumTextView extends TextView {


    /**
     * Instantiates a new Medium text view.
     *
     * @param context the context
     */
    public MediumTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Medium.otf");
        this.setTypeface(face);
    }

    /**
     * Instantiates a new Medium text view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public MediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Medium.otf");
        this.setTypeface(face);
    }

    /**
     * Instantiates a new Medium text view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public MediumTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Medium.otf");
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}