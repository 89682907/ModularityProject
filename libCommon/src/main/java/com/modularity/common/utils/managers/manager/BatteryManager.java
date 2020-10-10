package com.modularity.common.utils.managers.manager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.Set;

/**
 * 电池相关
 */
public final class BatteryManager {

    @IntDef({BatteryStatus.UNKNOWN, BatteryStatus.DISCHARGING, BatteryStatus.CHARGING,
            BatteryStatus.NOT_CHARGING, BatteryStatus.FULL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BatteryStatus {
        int UNKNOWN      = android.os.BatteryManager.BATTERY_STATUS_UNKNOWN;
        int DISCHARGING  = android.os.BatteryManager.BATTERY_STATUS_DISCHARGING;
        int CHARGING     = android.os.BatteryManager.BATTERY_STATUS_CHARGING;
        int NOT_CHARGING = android.os.BatteryManager.BATTERY_STATUS_NOT_CHARGING;
        int FULL         = android.os.BatteryManager.BATTERY_STATUS_FULL;
    }

    /**
     * Register the status of battery changed listener.
     *
     * @param listener The status of battery changed listener.
     */
    public static void registerBatteryStatusChangedListener(final OnBatteryStatusChangedListener listener) {
        BatteryChangedReceiver.getInstance().registerListener(listener);
    }

    /**
     * Return whether the status of battery changed listener has been registered.
     *
     * @param listener The status of battery changed listener.
     * @return true to registered, false otherwise.
     */
    public static boolean isRegistered(final OnBatteryStatusChangedListener listener) {
        return BatteryChangedReceiver.getInstance().isRegistered(listener);
    }

    /**
     * Unregister the status of battery changed listener.
     *
     * @param listener The status of battery changed listener.
     */
    public static void unregisterBatteryStatusChangedListener(final OnBatteryStatusChangedListener listener) {
        BatteryChangedReceiver.getInstance().unregisterListener(listener);
    }

    public static final class BatteryChangedReceiver extends BroadcastReceiver {

        private static BatteryChangedReceiver getInstance() {
            return LazyHolder.INSTANCE;
        }

        private Set<OnBatteryStatusChangedListener> mListeners = new HashSet<>();

        void registerListener(final OnBatteryStatusChangedListener listener) {
            if (listener == null) return;
            ThreadManager.runOnUiThread(new Runnable() {
                @SuppressLint("MissingPermission")
                @Override
                public void run() {
                    int preSize = mListeners.size();
                    mListeners.add(listener);
                    if (preSize == 0 && mListeners.size() == 1) {
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
                        Managers.getApp().registerReceiver(BatteryChangedReceiver.getInstance(), intentFilter);
                    }
                }
            });
        }

        boolean isRegistered(final OnBatteryStatusChangedListener listener) {
            if (listener == null) return false;
            return mListeners.contains(listener);
        }

        void unregisterListener(final OnBatteryStatusChangedListener listener) {
            if (listener == null) return;
            ThreadManager.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int preSize = mListeners.size();
                    mListeners.remove(listener);
                    if (preSize == 1 && mListeners.size() == 0) {
                        Managers.getApp().unregisterReceiver(BatteryChangedReceiver.getInstance());
                    }
                }
            });
        }

        @SuppressLint("MissingPermission")
        @Override
        public void onReceive(Context context, final Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                ThreadManager.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int level = intent.getIntExtra(android.os.BatteryManager.EXTRA_LEVEL, -1);
                        int status = intent.getIntExtra(android.os.BatteryManager.EXTRA_STATUS, BatteryStatus.UNKNOWN);
                        for (OnBatteryStatusChangedListener listener : mListeners) {
                            listener.onBatteryStatusChanged(new Status(level, status));
                        }
                    }
                });
            }
        }

        private static class LazyHolder {
            private static final BatteryChangedReceiver INSTANCE = new BatteryChangedReceiver();
        }
    }

    public interface OnBatteryStatusChangedListener {
        void onBatteryStatusChanged(Status status);
    }

    public static final class Status {
        private int level;
        @BatteryStatus
        private int status;

        Status(int level, int status) {
            this.level = level;
            this.status = status;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        @BatteryStatus
        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return batteryStatus2String(status) + ": " + level + "%";
        }

        public static String batteryStatus2String(@BatteryStatus int status) {
            if (status == BatteryStatus.DISCHARGING) {
                return "discharging";
            }
            if (status == BatteryStatus.CHARGING) {
                return "charging";
            }
            if (status == BatteryStatus.NOT_CHARGING) {
                return "not_charging";
            }
            if (status == BatteryStatus.FULL) {
                return "full";
            }
            return "unknown";
        }
    }
}
