package com.ilsa.grassis.library;

import android.view.View;

/**
 * The interface Menu item click listener.
 */
public interface MenuItemClickListener {

    /**
     * On click.
     *
     * @param view     the view
     * @param position the position
     */
    void onClick(View view, int position);

    /**
     * On long click.
     *
     * @param view     the view
     * @param position the position
     */
    void onLongClick(View view, int position);
}
