package com.modularity.signature.utils;

import androidx.databinding.BindingAdapter;

import com.modularity.signature.widget.SignatureView;

public final class SignaturePadBindingAdapter {

    @BindingAdapter("onStartSigning")
    public static void setOnSignedListener(SignatureView view, final OnStartSigningListener onStartSigningListener) {
        setOnSignedListener(view, onStartSigningListener, null, null);
    }

    @BindingAdapter("onSigned")
    public static void setOnSignedListener(SignatureView view, final OnSignedListener onSignedListener) {
        setOnSignedListener(view, null, onSignedListener, null);
    }

    @BindingAdapter("onClear")
    public static void setOnSignedListener(SignatureView view, final OnClearListener onClearListener) {
        setOnSignedListener(view, null, null, onClearListener);
    }

    @BindingAdapter(value = {"onStartSigning", "onSigned", "onClear"}, requireAll = false)
    public static void setOnSignedListener(SignatureView view, final OnStartSigningListener onStartSigningListener, final OnSignedListener onSignedListener, final OnClearListener onClearListener) {
        view.setOnSignedListener(new SignatureView.OnSignedListener() {
            @Override
            public void onStartSigning() {
                if (onStartSigningListener != null) {
                    onStartSigningListener.onStartSigning();
                }
            }

            @Override
            public void onSigned() {
                if (onSignedListener != null) {
                    onSignedListener.onSigned();
                }
            }

            @Override
            public void onClear() {
                if (onClearListener != null) {
                    onClearListener.onClear();
                }
            }
        });
    }

    public interface OnStartSigningListener {
        void onStartSigning();
    }

    public interface OnSignedListener {
        void onSigned();
    }

    public interface OnClearListener {
        void onClear();
    }

}
