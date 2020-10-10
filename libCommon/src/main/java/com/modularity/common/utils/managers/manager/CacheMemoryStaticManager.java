package com.modularity.common.utils.managers.manager;


import androidx.annotation.NonNull;

public final class CacheMemoryStaticManager {

    private static CacheMemoryManager sDefaultCacheMemoryManager;

    /**
     * Set the default instance of {@link CacheMemoryManager}.
     *
     * @param cacheMemoryManager The default instance of {@link CacheMemoryManager}.
     */
    public static void setDefaultCacheMemoryUtils(final CacheMemoryManager cacheMemoryManager) {
        sDefaultCacheMemoryManager = cacheMemoryManager;
    }

    /**
     * Put bytes in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Object value) {
        put(key, value, getDefaultCacheMemoryUtils());
    }

    /**
     * Put bytes in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Object value, int saveTime) {
        put(key, value, saveTime, getDefaultCacheMemoryUtils());
    }

    /**
     * Return the value in cache.
     *
     * @param key The key of cache.
     * @param <T> The value type.
     * @return the value if cache exists or null otherwise
     */
    public static <T> T get(@NonNull final String key) {
        return get(key, getDefaultCacheMemoryUtils());
    }

    /**
     * Return the value in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @param <T>          The value type.
     * @return the value if cache exists or defaultValue otherwise
     */
    public static <T> T get(@NonNull final String key, final T defaultValue) {
        return get(key, defaultValue, getDefaultCacheMemoryUtils());
    }

    /**
     * Return the count of cache.
     *
     * @return the count of cache
     */
    public static int getCacheCount() {
        return getCacheCount(getDefaultCacheMemoryUtils());
    }

    /**
     * Remove the cache by key.
     *
     * @param key The key of cache.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static Object remove(@NonNull final String key) {
        return remove(key, getDefaultCacheMemoryUtils());
    }

    /**
     * Clear all of the cache.
     */
    public static void clear() {
        clear(getDefaultCacheMemoryUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // dividing line
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put bytes in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheMemoryManager The instance of {@link CacheMemoryManager}.
     */
    public static void put(@NonNull final String key,
                           final Object value,
                           @NonNull final CacheMemoryManager cacheMemoryManager) {
        cacheMemoryManager.put(key, value);
    }

    /**
     * Put bytes in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheMemoryManager The instance of {@link CacheMemoryManager}.
     */
    public static void put(@NonNull final String key,
                           final Object value,
                           int saveTime,
                           @NonNull final CacheMemoryManager cacheMemoryManager) {
        cacheMemoryManager.put(key, value, saveTime);
    }

    /**
     * Return the value in cache.
     *
     * @param key              The key of cache.
     * @param cacheMemoryManager The instance of {@link CacheMemoryManager}.
     * @param <T>              The value type.
     * @return the value if cache exists or null otherwise
     */
    public static <T> T get(@NonNull final String key, @NonNull final CacheMemoryManager cacheMemoryManager) {
        return cacheMemoryManager.get(key);
    }

    /**
     * Return the value in cache.
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheMemoryManager The instance of {@link CacheMemoryManager}.
     * @param <T>              The value type.
     * @return the value if cache exists or defaultValue otherwise
     */
    public static <T> T get(@NonNull final String key,
                            final T defaultValue,
                            @NonNull final CacheMemoryManager cacheMemoryManager) {
        return cacheMemoryManager.get(key, defaultValue);
    }

    /**
     * Return the count of cache.
     *
     * @param cacheMemoryManager The instance of {@link CacheMemoryManager}.
     * @return the count of cache
     */
    public static int getCacheCount(@NonNull final CacheMemoryManager cacheMemoryManager) {
        return cacheMemoryManager.getCacheCount();
    }

    /**
     * Remove the cache by key.
     *
     * @param key              The key of cache.
     * @param cacheMemoryManager The instance of {@link CacheMemoryManager}.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static Object remove(@NonNull final String key, @NonNull final CacheMemoryManager cacheMemoryManager) {
        return cacheMemoryManager.remove(key);
    }

    /**
     * Clear all of the cache.
     *
     * @param cacheMemoryManager The instance of {@link CacheMemoryManager}.
     */
    public static void clear(@NonNull final CacheMemoryManager cacheMemoryManager) {
        cacheMemoryManager.clear();
    }

    private static CacheMemoryManager getDefaultCacheMemoryUtils() {
        return sDefaultCacheMemoryManager != null ? sDefaultCacheMemoryManager : CacheMemoryManager.getInstance();
    }
}