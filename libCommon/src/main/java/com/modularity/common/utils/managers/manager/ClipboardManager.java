package com.modularity.common.utils.managers.manager;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 粘贴板
 */
public final class ClipboardManager {

    private ClipboardManager() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 复制文本到剪贴板
     *
     * @param text 文本
     */
    public static void copyText(final CharSequence text) {
        android.content.ClipboardManager cm = (android.content.ClipboardManager) Managers.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("text", text));
    }

    /**
     * 获取剪贴板的文本
     *
     * @return 剪贴板的文本
     */
    public static CharSequence getText() {
        android.content.ClipboardManager cm = (android.content.ClipboardManager) Managers.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = cm.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).coerceToText(Managers.getApp());
        }
        return null;
    }

    /**
     * 复制uri到剪贴板
     *
     * @param uri uri
     */
    public static void copyUri(final Uri uri) {
        android.content.ClipboardManager cm = (android.content.ClipboardManager) Managers.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newUri(Managers.getApp().getContentResolver(), "uri", uri));
    }

    /**
     * 获取剪贴板的uri
     *
     * @return 剪贴板的uri
     */
    public static Uri getUri() {
        android.content.ClipboardManager cm = (android.content.ClipboardManager) Managers.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = cm.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getUri();
        }
        return null;
    }

    /**
     * 复制意图到剪贴板
     *
     * @param intent 意图
     */
    public static void copyIntent(final Intent intent) {
        android.content.ClipboardManager cm = (android.content.ClipboardManager) Managers.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newIntent("intent", intent));
    }

    /**
     * 获取剪贴板的意图
     *
     * @return 剪贴板的意图
     */
    public static Intent getIntent() {
        android.content.ClipboardManager cm = (android.content.ClipboardManager) Managers.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = cm.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getIntent();
        }
        return null;
    }
}
