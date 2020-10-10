package com.modularity.common.utils.managers.manager;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public final class CacheDiskStaticManager {

    private static CacheDiskManager sDefaultCacheDiskManager;

    /**
     * Set the default instance of {@link CacheDiskManager}.
     *
     * @param cacheDiskManager The default instance of {@link CacheDiskManager}.
     */
    public static void setDefaultCacheDiskUtils(final CacheDiskManager cacheDiskManager) {
        sDefaultCacheDiskManager = cacheDiskManager;
    }

    /**
     * Put bytes in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final byte[] value) {
        put(key, value, getDefaultCacheDiskUtils());
    }

    /**
     * Put bytes in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final byte[] value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDiskUtils());
    }

    /**
     * Return the bytes in cache.
     *
     * @param key The key of cache.
     * @return the bytes if cache exists or null otherwise
     */
    public static byte[] getBytes(@NonNull final String key) {
        return getBytes(key, getDefaultCacheDiskUtils());
    }

    /**
     * Return the bytes in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bytes if cache exists or defaultValue otherwise
     */
    public static byte[] getBytes(@NonNull final String key, final byte[] defaultValue) {
        return getBytes(key, defaultValue, getDefaultCacheDiskUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // about String
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put string value in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final String value) {
        put(key, value, getDefaultCacheDiskUtils());
    }

    /**
     * Put string value in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final String value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDiskUtils());
    }

    /**
     * Return the string value in cache.
     *
     * @param key The key of cache.
     * @return the string value if cache exists or null otherwise
     */
    public static String getString(@NonNull final String key) {
        return getString(key, getDefaultCacheDiskUtils());
    }

    /**
     * Return the string value in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the string value if cache exists or defaultValue otherwise
     */
    public static String getString(@NonNull final String key, final String defaultValue) {
        return getString(key, defaultValue, getDefaultCacheDiskUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // about JSONObject
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put JSONObject in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final JSONObject value) {
        put(key, value, getDefaultCacheDiskUtils());
    }

    /**
     * Put JSONObject in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key,
                           final JSONObject value,
                           final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDiskUtils());
    }

    /**
     * Return the JSONObject in cache.
     *
     * @param key The key of cache.
     * @return the JSONObject if cache exists or null otherwise
     */
    public static JSONObject getJSONObject(@NonNull final String key) {
        return getJSONObject(key, getDefaultCacheDiskUtils());
    }

    /**
     * Return the JSONObject in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the JSONObject if cache exists or defaultValue otherwise
     */
    public static JSONObject getJSONObject(@NonNull final String key, final JSONObject defaultValue) {
        return getJSONObject(key, defaultValue, getDefaultCacheDiskUtils());
    }


    ///////////////////////////////////////////////////////////////////////////
    // about JSONArray
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put JSONArray in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final JSONArray value) {
        put(key, value, getDefaultCacheDiskUtils());
    }

    /**
     * Put JSONArray in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final JSONArray value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDiskUtils());
    }

    /**
     * Return the JSONArray in cache.
     *
     * @param key The key of cache.
     * @return the JSONArray if cache exists or null otherwise
     */
    public static JSONArray getJSONArray(@NonNull final String key) {
        return getJSONArray(key, getDefaultCacheDiskUtils());
    }

    /**
     * Return the JSONArray in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the JSONArray if cache exists or defaultValue otherwise
     */
    public static JSONArray getJSONArray(@NonNull final String key, final JSONArray defaultValue) {
        return getJSONArray(key, defaultValue, getDefaultCacheDiskUtils());
    }


    ///////////////////////////////////////////////////////////////////////////
    // about Bitmap
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put bitmap in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Bitmap value) {
        put(key, value, getDefaultCacheDiskUtils());
    }

    /**
     * Put bitmap in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Bitmap value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDiskUtils());
    }

    /**
     * Return the bitmap in cache.
     *
     * @param key The key of cache.
     * @return the bitmap if cache exists or null otherwise
     */
    public static Bitmap getBitmap(@NonNull final String key) {
        return getBitmap(key, getDefaultCacheDiskUtils());
    }

    /**
     * Return the bitmap in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public static Bitmap getBitmap(@NonNull final String key, final Bitmap defaultValue) {
        return getBitmap(key, defaultValue, getDefaultCacheDiskUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Drawable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put drawable in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Drawable value) {
        put(key, value, getDefaultCacheDiskUtils());
    }

    /**
     * Put drawable in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Drawable value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDiskUtils());
    }

    /**
     * Return the drawable in cache.
     *
     * @param key The key of cache.
     * @return the drawable if cache exists or null otherwise
     */
    public static Drawable getDrawable(@NonNull final String key) {
        return getDrawable(key, getDefaultCacheDiskUtils());
    }

    /**
     * Return the drawable in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the drawable if cache exists or defaultValue otherwise
     */
    public static Drawable getDrawable(@NonNull final String key, final Drawable defaultValue) {
        return getDrawable(key, defaultValue, getDefaultCacheDiskUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Parcelable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put parcelable in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Parcelable value) {
        put(key, value, getDefaultCacheDiskUtils());
    }

    /**
     * Put parcelable in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Parcelable value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDiskUtils());
    }

    /**
     * Return the parcelable in cache.
     *
     * @param key     The key of cache.
     * @param creator The creator.
     * @param <T>     The value type.
     * @return the parcelable if cache exists or null otherwise
     */
    public static <T> T getParcelable(@NonNull final String key,
                                      @NonNull final Parcelable.Creator<T> creator) {
        return getParcelable(key, creator, getDefaultCacheDiskUtils());
    }

    /**
     * Return the parcelable in cache.
     *
     * @param key          The key of cache.
     * @param creator      The creator.
     * @param defaultValue The default value if the cache doesn't exist.
     * @param <T>          The value type.
     * @return the parcelable if cache exists or defaultValue otherwise
     */
    public static <T> T getParcelable(@NonNull final String key,
                                      @NonNull final Parcelable.Creator<T> creator,
                                      final T defaultValue) {
        return getParcelable(key, creator, defaultValue, getDefaultCacheDiskUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Serializable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put serializable in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Serializable value) {
        put(key, value, getDefaultCacheDiskUtils());
    }

    /**
     * Put serializable in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Serializable value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDiskUtils());
    }

    /**
     * Return the serializable in cache.
     *
     * @param key The key of cache.
     * @return the bitmap if cache exists or null otherwise
     */
    public static Object getSerializable(@NonNull final String key) {
        return getSerializable(key, getDefaultCacheDiskUtils());
    }

    /**
     * Return the serializable in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public static Object getSerializable(@NonNull final String key, final Object defaultValue) {
        return getSerializable(key, defaultValue, getDefaultCacheDiskUtils());
    }

    /**
     * Return the size of cache, in bytes.
     *
     * @return the size of cache, in bytes
     */
    public static long getCacheSize() {
        return getCacheSize(getDefaultCacheDiskUtils());
    }

    /**
     * Return the count of cache.
     *
     * @return the count of cache
     */
    public static int getCacheCount() {
        return getCacheCount(getDefaultCacheDiskUtils());
    }

    /**
     * Remove the cache by key.
     *
     * @param key The key of cache.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean remove(@NonNull final String key) {
        return remove(key, getDefaultCacheDiskUtils());
    }

    /**
     * Clear all of the cache.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean clear() {
        return clear(getDefaultCacheDiskUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // dividing line
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put bytes in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final byte[] value,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value);
    }

    /**
     * Put bytes in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param saveTime       The save time of cache, in seconds.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final byte[] value,
                           final int saveTime,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value, saveTime);
    }

    /**
     * Return the bytes in cache.
     *
     * @param key            The key of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the bytes if cache exists or null otherwise
     */
    public static byte[] getBytes(@NonNull final String key, @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getBytes(key);
    }

    /**
     * Return the bytes in cache.
     *
     * @param key            The key of cache.
     * @param defaultValue   The default value if the cache doesn't exist.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the bytes if cache exists or defaultValue otherwise
     */
    public static byte[] getBytes(@NonNull final String key,
                                  final byte[] defaultValue,
                                  @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getBytes(key, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // about String
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put string value in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final String value,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value);
    }

    /**
     * Put string value in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param saveTime       The save time of cache, in seconds.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final String value,
                           final int saveTime,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value, saveTime);
    }

    /**
     * Return the string value in cache.
     *
     * @param key            The key of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the string value if cache exists or null otherwise
     */
    public static String getString(@NonNull final String key, @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getString(key);
    }

    /**
     * Return the string value in cache.
     *
     * @param key            The key of cache.
     * @param defaultValue   The default value if the cache doesn't exist.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the string value if cache exists or defaultValue otherwise
     */
    public static String getString(@NonNull final String key,
                                   final String defaultValue,
                                   @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getString(key, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // about JSONObject
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put JSONObject in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final JSONObject value,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value);
    }

    /**
     * Put JSONObject in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param saveTime       The save time of cache, in seconds.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final JSONObject value,
                           final int saveTime,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value, saveTime);
    }

    /**
     * Return the JSONObject in cache.
     *
     * @param key            The key of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the JSONObject if cache exists or null otherwise
     */
    public static JSONObject getJSONObject(@NonNull final String key, @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getJSONObject(key);
    }

    /**
     * Return the JSONObject in cache.
     *
     * @param key            The key of cache.
     * @param defaultValue   The default value if the cache doesn't exist.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the JSONObject if cache exists or defaultValue otherwise
     */
    public static JSONObject getJSONObject(@NonNull final String key,
                                           final JSONObject defaultValue,
                                           @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getJSONObject(key, defaultValue);
    }


    ///////////////////////////////////////////////////////////////////////////
    // about JSONArray
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put JSONArray in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final JSONArray value,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value);
    }

    /**
     * Put JSONArray in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param saveTime       The save time of cache, in seconds.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final JSONArray value,
                           final int saveTime,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value, saveTime);
    }

    /**
     * Return the JSONArray in cache.
     *
     * @param key            The key of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the JSONArray if cache exists or null otherwise
     */
    public static JSONArray getJSONArray(@NonNull final String key, @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getJSONArray(key);
    }

    /**
     * Return the JSONArray in cache.
     *
     * @param key            The key of cache.
     * @param defaultValue   The default value if the cache doesn't exist.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the JSONArray if cache exists or defaultValue otherwise
     */
    public static JSONArray getJSONArray(@NonNull final String key,
                                         final JSONArray defaultValue,
                                         @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getJSONArray(key, defaultValue);
    }


    ///////////////////////////////////////////////////////////////////////////
    // about Bitmap
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put bitmap in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final Bitmap value,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value);
    }

    /**
     * Put bitmap in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param saveTime       The save time of cache, in seconds.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final Bitmap value,
                           final int saveTime,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value, saveTime);
    }

    /**
     * Return the bitmap in cache.
     *
     * @param key            The key of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the bitmap if cache exists or null otherwise
     */
    public static Bitmap getBitmap(@NonNull final String key, @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getBitmap(key);
    }

    /**
     * Return the bitmap in cache.
     *
     * @param key            The key of cache.
     * @param defaultValue   The default value if the cache doesn't exist.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public static Bitmap getBitmap(@NonNull final String key,
                                   final Bitmap defaultValue,
                                   @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getBitmap(key, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Drawable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put drawable in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final Drawable value,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value);
    }

    /**
     * Put drawable in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param saveTime       The save time of cache, in seconds.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final Drawable value,
                           final int saveTime,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value, saveTime);
    }

    /**
     * Return the drawable in cache.
     *
     * @param key            The key of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the drawable if cache exists or null otherwise
     */
    public static Drawable getDrawable(@NonNull final String key, @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getDrawable(key);
    }

    /**
     * Return the drawable in cache.
     *
     * @param key            The key of cache.
     * @param defaultValue   The default value if the cache doesn't exist.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the drawable if cache exists or defaultValue otherwise
     */
    public static Drawable getDrawable(@NonNull final String key,
                                       final Drawable defaultValue,
                                       @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getDrawable(key, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Parcelable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put parcelable in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final Parcelable value,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value);
    }

    /**
     * Put parcelable in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param saveTime       The save time of cache, in seconds.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final Parcelable value,
                           final int saveTime,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value, saveTime);
    }

    /**
     * Return the parcelable in cache.
     *
     * @param key            The key of cache.
     * @param creator        The creator.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @param <T>            The value type.
     * @return the parcelable if cache exists or null otherwise
     */
    public static <T> T getParcelable(@NonNull final String key,
                                      @NonNull final Parcelable.Creator<T> creator,
                                      @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getParcelable(key, creator);
    }

    /**
     * Return the parcelable in cache.
     *
     * @param key            The key of cache.
     * @param creator        The creator.
     * @param defaultValue   The default value if the cache doesn't exist.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @param <T>            The value type.
     * @return the parcelable if cache exists or defaultValue otherwise
     */
    public static <T> T getParcelable(@NonNull final String key,
                                      @NonNull final Parcelable.Creator<T> creator,
                                      final T defaultValue,
                                      @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getParcelable(key, creator, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Serializable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put serializable in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final Serializable value,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value);
    }

    /**
     * Put serializable in cache.
     *
     * @param key            The key of cache.
     * @param value          The value of cache.
     * @param saveTime       The save time of cache, in seconds.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     */
    public static void put(@NonNull final String key,
                           final Serializable value,
                           final int saveTime,
                           @NonNull final CacheDiskManager cacheDiskManager) {
        cacheDiskManager.put(key, value, saveTime);
    }

    /**
     * Return the serializable in cache.
     *
     * @param key            The key of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the bitmap if cache exists or null otherwise
     */
    public static Object getSerializable(@NonNull final String key, @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getSerializable(key);
    }

    /**
     * Return the serializable in cache.
     *
     * @param key            The key of cache.
     * @param defaultValue   The default value if the cache doesn't exist.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public static Object getSerializable(@NonNull final String key,
                                         final Object defaultValue,
                                         @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getSerializable(key, defaultValue);
    }

    /**
     * Return the size of cache, in bytes.
     *
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the size of cache, in bytes
     */
    public static long getCacheSize(@NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getCacheSize();
    }

    /**
     * Return the count of cache.
     *
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return the count of cache
     */
    public static int getCacheCount(@NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.getCacheCount();
    }

    /**
     * Remove the cache by key.
     *
     * @param key            The key of cache.
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean remove(@NonNull final String key, @NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.remove(key);
    }

    /**
     * Clear all of the cache.
     *
     * @param cacheDiskManager The instance of {@link CacheDiskManager}.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean clear(@NonNull final CacheDiskManager cacheDiskManager) {
        return cacheDiskManager.clear();
    }

    private static CacheDiskManager getDefaultCacheDiskUtils() {
        return sDefaultCacheDiskManager != null ? sDefaultCacheDiskManager : CacheDiskManager.getInstance();
    }
}