package com.ocse.baseandroid.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Function:
 * <br/>
 * Describe:键盘的显示-关闭-以及键盘高度的监听 工具类
 * <br/>
 * Author: reese on 2018/7/11.
 * <br/>
 */
public class KeyBordStateUtil {
    private OnKeyBordStateListener listener;
    private View rootLayout;
    private int mVisibleHeight, mFirstVisibleHeight;
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            calKeyBordState();
        }
    };

    public KeyBordStateUtil(Activity context) {
        rootLayout = ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
    }

    private void calKeyBordState() {
        Rect r = new Rect();
        rootLayout.getWindowVisibleDisplayFrame(r);
        int visibleHeight = r.height();
        if (mVisibleHeight == 0) {
            mVisibleHeight = visibleHeight;
            mFirstVisibleHeight = visibleHeight;
            return;
        }
        if (mVisibleHeight == visibleHeight) {
            return;
        }
        mVisibleHeight = visibleHeight;
        boolean mIsKeyboardShow = mVisibleHeight < mFirstVisibleHeight;
        if (mIsKeyboardShow) {
            //键盘高度
            int keyboardHeight = Math.abs(mVisibleHeight - mFirstVisibleHeight);
            if (listener != null) {
                listener.onSoftKeyBoardShow(keyboardHeight);
            }
        } else {
            if (listener != null) {
                listener.onSoftKeyBoardHide();
            }
        }
    }

    public void addOnKeyBordStateListener(OnKeyBordStateListener listener) {
        this.listener = listener;
    }

    public void removeOnKeyBordStateListener() {
        if (rootLayout != null && mOnGlobalLayoutListener != null) {
            rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
        }
        if (listener != null) {
            listener = null;
        }
    }

    public interface OnKeyBordStateListener {
        /**
         * 键盘显示
         *
         * @param keyboardHeight
         */
        void onSoftKeyBoardShow(int keyboardHeight);

        /**
         * 键盘隐藏
         */
        void onSoftKeyBoardHide();
    }
}
