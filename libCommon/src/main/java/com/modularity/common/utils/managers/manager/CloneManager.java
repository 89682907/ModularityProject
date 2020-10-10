package com.modularity.common.utils.managers.manager;

import java.lang.reflect.Type;

public final class CloneManager {

    private CloneManager() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Deep clone.
     *
     * @param data The data.
     * @param type The type.
     * @param <T>  The value type.
     * @return The object of cloned.
     */
    public static <T> T deepClone(final T data, final Type type) {
        try {
            return ManagerBridge.fromJson(ManagerBridge.toJson(data), type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
