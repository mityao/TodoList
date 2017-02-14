package com.example.android.todolist.utils;

import android.graphics.Paint;
import android.widget.TextView;

/**
 * Created by mitya on 12/16/2016.
 */

public class UIUtils {

    public static void setTextViewStrikeThrough(TextView tv, boolean strikeThrough) {
        if (strikeThrough) {
            //// strike through effect on the text
            tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            // no strike through effect
            tv.setPaintFlags((tv.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }
}
