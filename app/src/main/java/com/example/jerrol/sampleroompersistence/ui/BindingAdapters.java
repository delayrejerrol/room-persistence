package com.example.jerrol.sampleroompersistence.ui;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by Jerrol on 11/28/2017.
 */

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
