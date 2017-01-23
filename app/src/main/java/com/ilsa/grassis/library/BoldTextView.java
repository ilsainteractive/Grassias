package com.ilsa.grassis.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Bold text view with 'Montserrat-Bold' font style.
 */
public class BoldTextView extends TextView {


    /**
     * Instantiates a new Bold text view.
     *
     * @param context the context
     */
    public BoldTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Bold.otf");
        this.setTypeface(face);
    }

    /**
     * Instantiates a new Bold text view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public BoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Bold.otf");
        this.setTypeface(face);
    }

    /**
     * Instantiates a new Bold text view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public BoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Bold.otf");
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}