package com.ilsa.grassis.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Regular text view with font style `SFUIText-Regular.otf`
 */
public class RegularTextView extends TextView {

    /**
     * Instantiates a new Regular text view.
     *
     * @param context the context
     */
    public RegularTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Regular.otf");
        this.setTypeface(face);
    }

    /**
     * Instantiates a new Regular text view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public RegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Regular.otf");
        this.setTypeface(face);
    }

    /**
     * Instantiates a new Regular text view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public RegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Regular.otf");
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}