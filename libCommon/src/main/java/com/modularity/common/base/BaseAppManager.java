
package com.modularity.common.base;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

public class BaseAppManager {

    private static BaseAppManager instance = null;
    private static List<Activity> mActivities = new LinkedList<>();

    private BaseAppManager() {}

    public static BaseAppManager getInstance() {
        if (null == instance) {
            synchronized (BaseAppManager.class) {
                if (null == instance) {
                    instance = new BaseAppManager();
                }
            }
        }
        return instance;
    }

    public int size() {
        return mActivities.size();
    }

    public synchronized Activity getForwardActivity() {
        return size() > 0 ? mActivities.get(size() - 1) : null;
    }

    public synchronized void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public synchronized void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    public synchronized void clear() {
        for (int i = mActivities.size() - 1; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size();
        }
    }

    public synchronized void clearTop() {
        for (int i = mActivities.size() - 2; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size() - 1;
        }
    }

    public synchronized void clearBottom() {
        for (int i = mActivities.size() - 1; i > 0; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public synchronized void finishActivity(Class<?> cls) {
        for (Activity activity : mActivities) {
            if (activity.getClass().equals(cls)) {
                removeActivity(activity);
            }
        }
    }

    public synchronized boolean hasActivity(Class<?> cls){
        boolean flag = false;
        for (Activity activity : mActivities) {
            if (activity.getClass().equals(cls)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

//    /**
//     * 退出应用程序
//     */
//    public synchronized void AppExit(Context context) {
////        try {
////            clear();
////            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
////            activityMgr.killBackgroundProcesses(context.getPackageName());
////            System.exit(0);
////        } catch (Exception e) {
////            e.printStackTrace();
////            clear();
////        }
//        c
//    }

}
