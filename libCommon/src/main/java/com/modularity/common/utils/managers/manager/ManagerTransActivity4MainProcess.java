package com.modularity.common.utils.managers.manager;

import android.app.Activity;
import android.content.Intent;

public class ManagerTransActivity4MainProcess extends ManagerTransActivity {

    public static void start(final TransActivityDelegate delegate) {
        start(null, null, delegate, ManagerTransActivity4MainProcess.class);
    }

    public static void start(final Managers.Consumer<Intent> consumer,
                             final TransActivityDelegate delegate) {
        start(null, consumer, delegate, ManagerTransActivity4MainProcess.class);
    }

    public static void start(final Activity activity,
                             final TransActivityDelegate delegate) {
        start(activity, null, delegate, ManagerTransActivity4MainProcess.class);
    }

    public static void start(final Activity activity,
                             final Managers.Consumer<Intent> consumer,
                             final TransActivityDelegate delegate) {
        start(activity, consumer, delegate, ManagerTransActivity4MainProcess.class);
    }
}
