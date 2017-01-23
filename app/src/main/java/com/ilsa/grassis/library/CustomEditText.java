package com.ilsa.grassis.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Edit text with 'SFUIText-Regular' font style.
 */
public class CustomEditText extends EditText {
    /**
     * Instantiates a new Custom edit text.
     *
     * @param context the context
     */
    public CustomEditText(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Regular.otf");
        this.setTypeface(face);
    }

    /**
     * Instantiates a new Custom edit text.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Regular.otf");
        this.setTypeface(face);
    }

    /**
     * Instantiates a new Custom edit text.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SFUIText-Regular.otf");
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}