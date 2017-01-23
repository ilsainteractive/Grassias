package com.ilsa.grassis.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Thin TextView with font `SFUIText-Light.otf`.
 */
public class ThinTextView extends TextView {


    /**
     * Instantiates a new Thin text view.
     *
     * @param context the context
     */
    public ThinTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Light.otf");
        this.setTypeface(face);
    }

    /**
     * Instantiates a new Thin text view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public ThinTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Light.otf");
        this.setTypeface(face);
    }

    /**
     * Instantiates a new Thin text view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public ThinTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Light.otf");
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}