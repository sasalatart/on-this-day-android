package com.salatart.onthisday.Utils;

import android.app.Activity;
import android.os.Build;
import android.transition.Explode;

/**
 * Created by sasalatart on 1/31/17.
 */

public class TransitionUtils {
    public static void setExplodeTransition(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        Explode explodeTransition = new Explode();
        explodeTransition.setDuration(500);

        activity.getWindow().setExitTransition(explodeTransition);
        activity.getWindow().setReenterTransition(explodeTransition);
        activity.getWindow().setAllowReturnTransitionOverlap(false);
    }
}
