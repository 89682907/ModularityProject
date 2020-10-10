package com.modularity.common.utils.managers.manager;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.Map;
import java.util.Set;

public final class SPStaticManager {

    private static SPManager sDefaultSPManager;

    /**
     * Set the default instance of {@link SPManager}.
     *
     * @param spManager The default instance of {@link SPManager}.
     */
    public static void setDefaultSPUtils(final SPManager spManager) {
        sDefaultSPManager = spManager;
    }

    /**
     * Put the string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final String value) {
        put(key, value, getDefaultSPUtils());
    }

    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final String value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSPUtils());
    }


    /**
     * Return the string value in sp.
     *
     * @param key The key of sp.
     * @return the string value if sp exists or {@code ""} otherwise
     */
    public static String getString(@NonNull final String key) {
        return getString(key, getDefaultSPUtils());
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the string value if sp exists or {@code defaultValue} otherwise
     */
    public static String getString(@NonNull final String key, final String defaultValue) {
        return getString(key, defaultValue, getDefaultSPUtils());
    }


    /**
     * Put the int value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final int value) {
        put(key, value, getDefaultSPUtils());
    }

    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final int value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * Return the int value in sp.
     *
     * @param key The key of sp.
     * @return the int value if sp exists or {@code -1} otherwise
     */
    public static int getInt(@NonNull final String key) {
        return getInt(key, getDefaultSPUtils());
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the int value if sp exists or {@code defaultValue} otherwise
     */
    public static int getInt(@NonNull final String key, final int defaultValue) {
        return getInt(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * Put the long value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final long value) {
        put(key, value, getDefaultSPUtils());
    }

    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final long value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * Return the long value in sp.
     *
     * @param key The key of sp.
     * @return the long value if sp exists or {@code -1} otherwise
     */
    public static long getLong(@NonNull final String key) {
        return getLong(key, getDefaultSPUtils());
    }

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the long value if sp exists or {@code defaultValue} otherwise
     */
    public static long getLong(@NonNull final String key, final long defaultValue) {
        return getLong(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * Put the float value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final float value) {
        put(key, value, getDefaultSPUtils());
    }

    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final float value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * Return the float value in sp.
     *
     * @param key The key of sp.
     * @return the float value if sp exists or {@code -1f} otherwise
     */
    public static float getFloat(@NonNull final String key) {
        return getFloat(key, getDefaultSPUtils());
    }

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the float value if sp exists or {@code defaultValue} otherwise
     */
    public static float getFloat(@NonNull final String key, final float defaultValue) {
        return getFloat(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final boolean value) {
        put(key, value, getDefaultSPUtils());
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final boolean value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key The key of sp.
     * @return the boolean value if sp exists or {@code false} otherwise
     */
    public static boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, getDefaultSPUtils());
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the boolean value if sp exists or {@code defaultValue} otherwise
     */
    public static boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return getBoolean(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final Set<String> value) {
        put(key, value, getDefaultSPUtils());
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key,
                           final Set<String> value,
                           final boolean isCommit) {
        put(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key The key of sp.
     * @return the set of string value if sp exists
     * or {@code Collections.<String>emptySet()} otherwise
     */
    public static Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, getDefaultSPUtils());
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the set of string value if sp exists or {@code defaultValue} otherwise
     */
    public static Set<String> getStringSet(@NonNull final String key,
                                           final Set<String> defaultValue) {
        return getStringSet(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * Return all values in sp.
     *
     * @return all values in sp
     */
    public static Map<String, ?> getAll() {
        return getAll(getDefaultSPUtils());
    }

    /**
     * Return whether the sp contains the preference.
     *
     * @param key The key of sp.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean contains(@NonNull final String key) {
        return contains(key, getDefaultSPUtils());
    }

    /**
     * Remove the preference in sp.
     *
     * @param key The key of sp.
     */
    public static void remove(@NonNull final String key) {
        remove(key, getDefaultSPUtils());
    }

    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void remove(@NonNull final String key, final boolean isCommit) {
        remove(key, isCommit, getDefaultSPUtils());
    }

    /**
     * Remove all preferences in sp.
     */
    public static void clear() {
        clear(getDefaultSPUtils());
    }

    /**
     * Remove all preferences in sp.
     *
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void clear(final boolean isCommit) {
        clear(isCommit, getDefaultSPUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // dividing line
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put the string value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spManager The instance of {@link SPManager}.
     */
    public static void put(@NonNull final String key, final String value, @NonNull final SPManager spManager) {
        spManager.put(key, value);
    }

    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spManager  The instance of {@link SPManager}.
     */
    public static void put(@NonNull final String key,
                           final String value,
                           final boolean isCommit,
                           @NonNull final SPManager spManager) {
        spManager.put(key, value, isCommit);
    }


    /**
     * Return the string value in sp.
     *
     * @param key     The key of sp.
     * @param spManager The instance of {@link SPManager}.
     * @return the string value if sp exists or {@code ""} otherwise
     */
    public static String getString(@NonNull final String key, @NonNull final SPManager spManager) {
        return spManager.getString(key);
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spManager      The instance of {@link SPManager}.
     * @return the string value if sp exists or {@code defaultValue} otherwise
     */
    public static String getString(@NonNull final String key,
                                   final String defaultValue,
                                   @NonNull final SPManager spManager) {
        return spManager.getString(key, defaultValue);
    }


    /**
     * Put the int value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spManager The instance of {@link SPManager}.
     */
    public static void put(@NonNull final String key, final int value, @NonNull final SPManager spManager) {
        spManager.put(key, value);
    }

    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spManager  The instance of {@link SPManager}.
     */
    public static void put(@NonNull final String key,
                           final int value,
                           final boolean isCommit,
                           @NonNull final SPManager spManager) {
        spManager.put(key, value, isCommit);
    }

    /**
     * Return the int value in sp.
     *
     * @param key     The key of sp.
     * @param spManager The instance of {@link SPManager}.
     * @return the int value if sp exists or {@code -1} otherwise
     */
    public static int getInt(@NonNull final String key, @NonNull final SPManager spManager) {
        return spManager.getInt(key);
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spManager      The instance of {@link SPManager}.
     * @return the int value if sp exists or {@code defaultValue} otherwise
     */
    public static int getInt(@NonNull final String key, final int defaultValue, @NonNull final SPManager spManager) {
        return spManager.getInt(key, defaultValue);
    }

    /**
     * Put the long value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spManager The instance of {@link SPManager}.
     */
    public static void put(@NonNull final String key, final long value, @NonNull final SPManager spManager) {
        spManager.put(key, value);
    }

    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spManager  The instance of {@link SPManager}.
     */
    public static void put(@NonNull final String key,
                           final long value,
                           final boolean isCommit,
                           @NonNull final SPManager spManager) {
        spManager.put(key, value, isCommit);
    }

    /**
     * Return the long value in sp.
     *
     * @param key     The key of sp.
     * @param spManager The instance of {@link SPManager}.
     * @return the long value if sp exists or {@code -1} otherwise
     */
    public static long getLong(@NonNull final String key, @NonNull final SPManager spManager) {
        return spManager.getLong(key);
    }

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spManager      The instance of {@link SPManager}.
     * @return the long value if sp exists or {@code defaultValue} otherwise
     */
    public static long getLong(@NonNull final String key, final long defaultValue, @NonNull final SPManager spManager) {
        return spManager.getLong(key, defaultValue);
    }

    /**
     * Put the float value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spManager The instance of {@link SPManager}.
     */
    public static void put(@NonNull final String key, final float value, @NonNull final SPManager spManager) {
        spManager.put(key, value);
    }

    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spManager  The instance of {@link SPManager}.
     */
    public static void put(@NonNull final String key,
                           final float value,
                           final boolean isCommit,
                           @NonNull final SPManager spManager) {
        spManager.put(key, value, isCommit);
    }

    /**
     * Return the float value in sp.
     *
     * @param key     The key of sp.
     * @param spManager The instance of {@link SPManager}.
     * @return the float value if sp exists or {@code -1f} otherwise
     */
    public static float getFloat(@NonNull final String key, @NonNull final SPManager spManager) {
        return spManager.getFloat(key);
    }

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spManager      The instance of {@link SPManager}.
     * @return the float value if sp exists or {@code defaultValue} otherwise
     */
    public static float getFloat(@NonNull final String key, final float defaultValue, @NonNull final SPManager spManager) {
        return spManager.getFloat(key, defaultValue);
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spManager The instance of {@link SPManager}.
     */
    public static void put(@NonNull final String key, final boolean value, @NonNull final SPManager spManager) {
        spManager.put(key, value);
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spManager  The instance of {@link SPManager}.
     */
    public static void put(@NonNull final String key,
                           final boolean value,
                           final boolean isCommit,
                           @NonNull final SPManager spManager) {
        spManager.put(key, value, isCommit);
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key     The key of sp.
     * @param spManager The instance of {@link SPManager}.
     * @return the boolean value if sp exists or {@code false} otherwise
     */
    public static boolean getBoolean(@NonNull final String key, @NonNull final SPManager spManager) {
        return spManager.getBoolean(key);
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spManager      The instance of {@link SPManager}.
     * @return the boolean value if sp exists or {@code defaultValue} otherwise
     */
    public static boolean getBoolean(@NonNull final String key,
                                     final boolean defaultValue,
                                     @NonNull final SPManager spManager) {
        return spManager.getBoolean(key, defaultValue);
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spManager The instance of {@link SPManager}.
     */
    public static void put(@NonNull final String key, final Set<String> value, @NonNull final SPManager spManager) {
        spManager.put(key, value);
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spManager  The instance of {@link SPManager}.
     */
    public static void put(@NonNull final String key,
                           final Set<String> value,
                           final boolean isCommit,
                           @NonNull final SPManager spManager) {
        spManager.put(key, value, isCommit);
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key     The key of sp.
     * @param spManager The instance of {@link SPManager}.
     * @return the set of string value if sp exists
     * or {@code Collections.<String>emptySet()} otherwise
     */
    public static Set<String> getStringSet(@NonNull final String key, @NonNull final SPManager spManager) {
        return spManager.getStringSet(key);
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spManager      The instance of {@link SPManager}.
     * @return the set of string value if sp exists or {@code defaultValue} otherwise
     */
    public static Set<String> getStringSet(@NonNull final String key,
                                           final Set<String> defaultValue,
                                           @NonNull final SPManager spManager) {
        return spManager.getStringSet(key, defaultValue);
    }

    /**
     * Return all values in sp.
     *
     * @param spManager The instance of {@link SPManager}.
     * @return all values in sp
     */
    public static Map<String, ?> getAll(@NonNull final SPManager spManager) {
        return spManager.getAll();
    }

    /**
     * Return whether the sp contains the preference.
     *
     * @param key     The key of sp.
     * @param spManager The instance of {@link SPManager}.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean contains(@NonNull final String key, @NonNull final SPManager spManager) {
        return spManager.contains(key);
    }

    /**
     * Remove the preference in sp.
     *
     * @param key     The key of sp.
     * @param spManager The instance of {@link SPManager}.
     */
    public static void remove(@NonNull final String key, @NonNull final SPManager spManager) {
        spManager.remove(key);
    }

    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spManager  The instance of {@link SPManager}.
     */
    public static void remove(@NonNull final String key, final boolean isCommit, @NonNull final SPManager spManager) {
        spManager.remove(key, isCommit);
    }

    /**
     * Remove all preferences in sp.
     *
     * @param spManager The instance of {@link SPManager}.
     */
    public static void clear(@NonNull final SPManager spManager) {
        spManager.clear();
    }

    /**
     * Remove all preferences in sp.
     *
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spManager  The instance of {@link SPManager}.
     */
    public static void clear(final boolean isCommit, @NonNull final SPManager spManager) {
        spManager.clear(isCommit);
    }

    private static SPManager getDefaultSPUtils() {
        return sDefaultSPManager != null ? sDefaultSPManager : SPManager.getInstance();
    }
}