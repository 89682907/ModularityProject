package com.modularity.common.utils.managers.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;

class ManagerBridge {

    static void init(Application app) {
        ManagerActivityLifecycleImpl.INSTANCE.init(app);
    }

    static void unInit(Application app) {
        ManagerActivityLifecycleImpl.INSTANCE.unInit(app);
    }

    static void preLoad() {
        preLoad(AdaptScreenManager.getPreLoadRunnable());
    }

    ///////////////////////////////////////////////////////////////////////////
    // UtilsActivityLifecycleImpl
    ///////////////////////////////////////////////////////////////////////////
    static Activity getTopActivity() {
        return ManagerActivityLifecycleImpl.INSTANCE.getTopActivity();
    }

    static void addOnAppStatusChangedListener(final Managers.OnAppStatusChangedListener listener) {
        ManagerActivityLifecycleImpl.INSTANCE.addOnAppStatusChangedListener(listener);
    }

    static void removeOnAppStatusChangedListener(final Managers.OnAppStatusChangedListener listener) {
        ManagerActivityLifecycleImpl.INSTANCE.removeOnAppStatusChangedListener(listener);
    }

    static void addActivityLifecycleCallbacks(final Activity activity,
                                              final Managers.ActivityLifecycleCallbacks callbacks) {
        ManagerActivityLifecycleImpl.INSTANCE.addActivityLifecycleCallbacks(activity, callbacks);
    }

    static void removeActivityLifecycleCallbacks(final Activity activity) {
        ManagerActivityLifecycleImpl.INSTANCE.removeActivityLifecycleCallbacks(activity);
    }

    static void removeActivityLifecycleCallbacks(final Activity activity,
                                                 final Managers.ActivityLifecycleCallbacks callbacks) {
        ManagerActivityLifecycleImpl.INSTANCE.removeActivityLifecycleCallbacks(activity, callbacks);
    }

    static List<Activity> getActivityList() {
        return ManagerActivityLifecycleImpl.INSTANCE.getActivityList();
    }

    static Application getApplicationByReflect() {
        return ManagerActivityLifecycleImpl.INSTANCE.getApplicationByReflect();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ActivityUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isActivityAlive(final Activity activity) {
        return ActivityManager.isActivityAlive(activity);
    }

    static String getLauncherActivity() {
        return ActivityManager.getLauncherActivity();
    }

    static String getLauncherActivity(final String pkg) {
        return ActivityManager.getLauncherActivity(pkg);
    }

    static Activity getActivityByContext(Context context) {
        return ActivityManager.getActivityByContext(context);
    }

    static void startHomeActivity() {
        ActivityManager.startHomeActivity();
    }

    static void finishAllActivities() {
        ActivityManager.finishAllActivities();
    }

    ///////////////////////////////////////////////////////////////////////////
    // AppUtils
    ///////////////////////////////////////////////////////////////////////////
    static Context getTopActivityOrApp() {
        if (AppManager.isAppForeground()) {
            Activity topActivity = getTopActivity();
            return topActivity == null ? Managers.getApp() : topActivity;
        } else {
            return Managers.getApp();
        }
    }

    static boolean isAppRunning(@NonNull final String pkgName) {
        return AppManager.isAppRunning(pkgName);
    }

    static boolean isAppInstalled(final String pkgName) {
        return AppManager.isAppInstalled(pkgName);
    }

    static String getAppVersionName() {
        return AppManager.getAppVersionName();
    }

    static int getAppVersionCode() {
        return AppManager.getAppVersionCode();
    }

    static boolean isAppDebug() {
        return AppManager.isAppDebug();
    }

    ///////////////////////////////////////////////////////////////////////////
    // BarUtils
    ///////////////////////////////////////////////////////////////////////////
    static int getStatusBarHeight() {
        return BarManager.getStatusBarHeight();
    }

    static int getNavBarHeight() {
        return BarManager.getNavBarHeight();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ConvertUtils
    ///////////////////////////////////////////////////////////////////////////
    static String bytes2HexString(final byte[] bytes) {
        return ConvertManager.bytes2HexString(bytes);
    }

    static byte[] hexString2Bytes(String hexString) {
        return ConvertManager.hexString2Bytes(hexString);
    }

    static byte[] string2Bytes(final String string) {
        return ConvertManager.string2Bytes(string);
    }

    static String bytes2String(final byte[] bytes) {
        return ConvertManager.bytes2String(bytes);
    }

    static byte[] jsonObject2Bytes(final JSONObject jsonObject) {
        return ConvertManager.jsonObject2Bytes(jsonObject);
    }

    static JSONObject bytes2JSONObject(final byte[] bytes) {
        return ConvertManager.bytes2JSONObject(bytes);
    }

    static byte[] jsonArray2Bytes(final JSONArray jsonArray) {
        return ConvertManager.jsonArray2Bytes(jsonArray);
    }

    static JSONArray bytes2JSONArray(final byte[] bytes) {
        return ConvertManager.bytes2JSONArray(bytes);
    }

    static byte[] parcelable2Bytes(final Parcelable parcelable) {
        return ConvertManager.parcelable2Bytes(parcelable);
    }

    static <T> T bytes2Parcelable(final byte[] bytes,
                                  final Parcelable.Creator<T> creator) {
        return ConvertManager.bytes2Parcelable(bytes, creator);
    }

    static byte[] serializable2Bytes(final Serializable serializable) {
        return ConvertManager.serializable2Bytes(serializable);
    }

    static Object bytes2Object(final byte[] bytes) {
        return ConvertManager.bytes2Object(bytes);
    }

    static String byte2FitMemorySize(final long byteSize) {
        return ConvertManager.byte2FitMemorySize(byteSize);
    }

    static byte[] inputStream2Bytes(final InputStream is) {
        return ConvertManager.inputStream2Bytes(is);
    }

    static ByteArrayOutputStream input2OutputStream(final InputStream is) {
        return ConvertManager.input2OutputStream(is);
    }

    static List<String> inputStream2Lines(final InputStream is, final String charsetName) {
        return ConvertManager.inputStream2Lines(is, charsetName);
    }

    ///////////////////////////////////////////////////////////////////////////
    // DebouncingUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isValid(@NonNull final View view, final long duration) {
        return DebouncingManager.isValid(view, duration);
    }

    ///////////////////////////////////////////////////////////////////////////
    // EncodeUtils
    ///////////////////////////////////////////////////////////////////////////
    static byte[] base64Encode(final byte[] input) {
        return EncodeManager.base64Encode(input);
    }

    static byte[] base64Decode(final byte[] input) {
        return EncodeManager.base64Decode(input);
    }

    ///////////////////////////////////////////////////////////////////////////
    // EncryptUtils
    ///////////////////////////////////////////////////////////////////////////
    static byte[] hashTemplate(final byte[] data, final String algorithm) {
        return EncryptManager.hashTemplate(data, algorithm);
    }

    ///////////////////////////////////////////////////////////////////////////
    // FileIOUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean writeFileFromBytes(final File file,
                                      final byte[] bytes) {
        return FileIOManager.writeFileFromBytesByChannel(file, bytes, true);
    }

    static byte[] readFile2Bytes(final File file) {
        return FileIOManager.readFile2BytesByChannel(file);
    }

    static boolean writeFileFromString(final String filePath, final String content, final boolean append) {
        return FileIOManager.writeFileFromString(filePath, content, append);
    }

    static boolean writeFileFromIS(final String filePath, final InputStream is) {
        return FileIOManager.writeFileFromIS(filePath, is);
    }

    ///////////////////////////////////////////////////////////////////////////
    // FileUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isFileExists(final File file) {
        return FileManager.isFileExists(file);
    }

    static File getFileByPath(final String filePath) {
        return FileManager.getFileByPath(filePath);
    }

    static boolean deleteAllInDir(final File dir) {
        return FileManager.deleteAllInDir(dir);
    }

    static boolean createOrExistsFile(final File file) {
        return FileManager.createOrExistsFile(file);
    }

    static boolean createOrExistsDir(final File file) {
        return FileManager.createOrExistsDir(file);
    }

    static boolean createFileByDeleteOldFile(final File file) {
        return FileManager.createFileByDeleteOldFile(file);
    }

    static long getFsTotalSize(String path) {
        return FileManager.getFsTotalSize(path);
    }

    static long getFsAvailableSize(String path) {
        return FileManager.getFsAvailableSize(path);
    }

    static void notifySystemToScan(File file) {
        FileManager.notifySystemToScan(file);
    }

    ///////////////////////////////////////////////////////////////////////////
    // GsonUtils
    ///////////////////////////////////////////////////////////////////////////
    static String toJson(final Object object) {
        return GsonManager.toJson(object);
    }

    static <T> T fromJson(final String json, final Type type) {
        return GsonManager.fromJson(json, type);
    }

    static Gson getGson4LogUtils() {
        return GsonManager.getGson4LogUtils();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ImageUtils
    ///////////////////////////////////////////////////////////////////////////
    static byte[] bitmap2Bytes(final Bitmap bitmap) {
        return ImageManager.bitmap2Bytes(bitmap);
    }

    static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format, int quality) {
        return ImageManager.bitmap2Bytes(bitmap, format, quality);
    }

    static Bitmap bytes2Bitmap(final byte[] bytes) {
        return ImageManager.bytes2Bitmap(bytes);
    }

    static byte[] drawable2Bytes(final Drawable drawable) {
        return ImageManager.drawable2Bytes(drawable);
    }

    static byte[] drawable2Bytes(final Drawable drawable, final Bitmap.CompressFormat format, int quality) {
        return ImageManager.drawable2Bytes(drawable, format, quality);
    }

    static Drawable bytes2Drawable(final byte[] bytes) {
        return ImageManager.bytes2Drawable(bytes);
    }

    static Bitmap view2Bitmap(final View view) {
        return ImageManager.view2Bitmap(view);
    }

    static Bitmap drawable2Bitmap(final Drawable drawable) {
        return ImageManager.drawable2Bitmap(drawable);
    }

    static Drawable bitmap2Drawable(final Bitmap bitmap) {
        return ImageManager.bitmap2Drawable(bitmap);
    }

    ///////////////////////////////////////////////////////////////////////////
    // IntentUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isIntentAvailable(final Intent intent) {
        return IntentManager.isIntentAvailable(intent);
    }

    static Intent getLaunchAppIntent(final String pkgName) {
        return IntentManager.getLaunchAppIntent(pkgName);
    }

    static Intent getInstallAppIntent(final File file) {
        return IntentManager.getInstallAppIntent(file);
    }

    static Intent getInstallAppIntent(final Uri uri) {
        return IntentManager.getInstallAppIntent(uri);
    }

    static Intent getUninstallAppIntent(final String pkgName) {
        return IntentManager.getUninstallAppIntent(pkgName);
    }

    static Intent getDialIntent(final String phoneNumber) {
        return IntentManager.getDialIntent(phoneNumber);
    }

    @RequiresPermission(CALL_PHONE)
    static Intent getCallIntent(final String phoneNumber) {
        return IntentManager.getCallIntent(phoneNumber);
    }

    static Intent getSendSmsIntent(final String phoneNumber, final String content) {
        return IntentManager.getSendSmsIntent(phoneNumber, content);
    }

    static Intent getLaunchAppDetailsSettingsIntent(final String pkgName, final boolean isNewTask) {
        return IntentManager.getLaunchAppDetailsSettingsIntent(pkgName, isNewTask);
    }


    ///////////////////////////////////////////////////////////////////////////
    // JsonUtils
    ///////////////////////////////////////////////////////////////////////////
    static String formatJson(String json) {
        return JsonManager.formatJson(json);
    }

    ///////////////////////////////////////////////////////////////////////////
    // KeyboardUtils
    ///////////////////////////////////////////////////////////////////////////
    static void fixSoftInputLeaks(final Activity activity) {
        KeyboardManager.fixSoftInputLeaks(activity);
    }

    ///////////////////////////////////////////////////////////////////////////
    // LanguageUtils
    ///////////////////////////////////////////////////////////////////////////
    static void applyLanguage(final Activity activity) {
        LanguageManager.applyLanguage(activity);
    }

    ///////////////////////////////////////////////////////////////////////////
    // PermissionUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isGranted(final String... permissions) {
        return PermissionManager.isGranted(permissions);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    static boolean isGrantedDrawOverlays() {
        return PermissionManager.isGrantedDrawOverlays();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ProcessUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isMainProcess() {
        return ProcessManager.isMainProcess();
    }

    static String getForegroundProcessName() {
        return ProcessManager.getForegroundProcessName();
    }

    static String getCurrentProcessName() {
        return ProcessManager.getCurrentProcessName();
    }

    ///////////////////////////////////////////////////////////////////////////
    // RomUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isSamsung() {
        return RomManager.isSamsung();
    }

    ///////////////////////////////////////////////////////////////////////////
    // SDCardUtils
    ///////////////////////////////////////////////////////////////////////////
    static String getSDCardPathByEnvironment() {
        return SDCardManager.getSDCardPathByEnvironment();
    }

    static boolean isSDCardEnableByEnvironment() {
        return SDCardManager.isSDCardEnableByEnvironment();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ServiceUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isServiceRunning(final String className) {
        return ServiceManager.isServiceRunning(className);
    }

    ///////////////////////////////////////////////////////////////////////////
    // ShellUtils
    ///////////////////////////////////////////////////////////////////////////
    static ShellManager.CommandResult execCmd(final String command, final boolean isRooted) {
        return ShellManager.execCmd(command, isRooted);
    }

    ///////////////////////////////////////////////////////////////////////////
    // SizeUtils
    ///////////////////////////////////////////////////////////////////////////
    static int dp2px(final float dpValue) {
        return SizeManager.dp2px(dpValue);
    }

    static int px2dp(final float pxValue) {
        return SizeManager.px2dp(pxValue);
    }

    static int sp2px(final float spValue) {
        return SizeManager.sp2px(spValue);
    }

    static int px2sp(final float pxValue) {
        return SizeManager.px2sp(pxValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // SpUtils
    ///////////////////////////////////////////////////////////////////////////
    static SPManager getSpUtils4Utils() {
        return SPManager.getInstance("Utils");
    }

    ///////////////////////////////////////////////////////////////////////////
    // StringUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isSpace(final String s) {
        return StringManager.isSpace(s);
    }

    static boolean equals(final CharSequence s1, final CharSequence s2) {
        return StringManager.equals(s1, s2);
    }


    ///////////////////////////////////////////////////////////////////////////
    // ThreadUtils
    ///////////////////////////////////////////////////////////////////////////
    static <T> Managers.Task<T> doAsync(final Managers.Task<T> task) {
        ThreadManager.getCachedPool().execute(task);
        return task;
    }

    static void runOnUiThread(final Runnable runnable) {
        ThreadManager.runOnUiThread(runnable);
    }

    static void runOnUiThreadDelayed(final Runnable runnable, long delayMillis) {
        ThreadManager.runOnUiThreadDelayed(runnable, delayMillis);
    }

    ///////////////////////////////////////////////////////////////////////////
    // ThrowableUtils
    ///////////////////////////////////////////////////////////////////////////
    static String getFullStackTrace(Throwable throwable) {
        return ThrowableManager.getFullStackTrace(throwable);
    }

    ///////////////////////////////////////////////////////////////////////////
    // TimeUtils
    ///////////////////////////////////////////////////////////////////////////
    static String millis2FitTimeSpan(long millis, int precision) {
        return TimeManager.millis2FitTimeSpan(millis, precision);
    }

    ///////////////////////////////////////////////////////////////////////////
    // ToastUtils
    ///////////////////////////////////////////////////////////////////////////
    static void toastShowShort(final CharSequence text) {
        ToastManager.showShort(text);
    }

    static void toastCancel() {
        ToastManager.cancel();
    }

    private static void preLoad(final Runnable... runs) {
        for (final Runnable r : runs) {
            ThreadManager.getCachedPool().execute(r);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // UriUtils
    ///////////////////////////////////////////////////////////////////////////
    static Uri file2Uri(final File file) {
        return UriManager.file2Uri(file);
    }

    static File uri2File(final Uri uri) {
        return UriManager.uri2File(uri);
    }
}
