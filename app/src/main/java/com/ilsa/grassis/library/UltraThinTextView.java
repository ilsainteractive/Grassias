package com.ilsa.grassis.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * ultra thin TextView with font `SFUIDisplay-Ultralight.otf`.
 */
public class UltraThinTextView extends TextView {


    /**
     * Instantiates a new Ultra thin text view.
     *
     * @param context the context
     */
    public UltraThinTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIDisplay-Ultralight.otf");
        this.setTypeface(face);
    }

    /**
     * Instantiates a new Ultra thin text view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public UltraThinTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIDisplay-Ultralight.otf");
        this.setTypeface(face);
    }

    /**
     * Instantiates a new Ultra thin text view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public UltraThinTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIDisplay-Ultralight.otf");
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}