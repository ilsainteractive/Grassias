package com.ilsa.grassis.library;

import android.view.View;

/**
 * Created by Ilsa on 1/18/2017.
 */

public interface MenuItemClickListener {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
