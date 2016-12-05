package com.ekeitho.headerfooterview;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Random;

// Extra class to hide away unnecessary details
class Utils {

    final static int HEADER = 0;
    final static int ITEM = 1;
    final static int FOOTER = 2;
    final static int BOTH = 3;

    static View createDynamicView(Activity activity) {
        LinearLayout layout = new LinearLayout(activity);
        layout.setBackgroundColor(Color.parseColor("#000000"));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(activity.getResources(), 200)));
        return layout;
    }

    static int dpToPx(Resources resources, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    static String colorGenerator() {
        char[] hexCodes = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int hexLength = 6;
        Random random = new Random();
        String color = "";

        for (int i = 0; i < hexLength; i++) {
            color += hexCodes[random.nextInt(hexCodes.length)];
        }
        return "#" + color;
    }

    static int getSetupMode(View headerView, int footerViewResource) {
        if (headerView != null & footerViewResource > 0) {
            return BOTH;
        } else if (headerView != null) {
            return HEADER;
        } else if (footerViewResource > 0) {
            return FOOTER;
        }
        // if none, just item mode
        return ITEM;
    }
}
