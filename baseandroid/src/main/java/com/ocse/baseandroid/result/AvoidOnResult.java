package com.ocse.baseandroid.result;

import android.content.Intent;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import io.reactivex.Observable;

/**
 * @author hujiayi
 */
public class AvoidOnResult {
    private static final String TAG = "AvoidOnResult";
    private AvoidOnResultFragment mAvoidOnResultFragment;

    public AvoidOnResult(FragmentActivity activity) {
        mAvoidOnResultFragment = getAvoidOnResultFragment(activity);
    }


    private AvoidOnResultFragment getAvoidOnResultFragment(FragmentActivity activity) {
        AvoidOnResultFragment avoidOnResultFragment = findAvoidOnResultFragment(activity);
        if (avoidOnResultFragment == null) {
            avoidOnResultFragment = new AvoidOnResultFragment();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(avoidOnResultFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return avoidOnResultFragment;
    }

    private AvoidOnResultFragment findAvoidOnResultFragment(FragmentActivity activity) {
        return (AvoidOnResultFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG);
    }

    public Observable<ActivityResultInfo> startForResult(Intent intent) {
        return mAvoidOnResultFragment.startForResult(intent);
    }

    public Observable<ActivityResultInfo> startForResult(Class<?> clazz) {
        Intent intent = new Intent(mAvoidOnResultFragment.getActivity(), clazz);
        return startForResult(intent);
    }

    public void startForResult(Intent intent, Callback callback) {
        mAvoidOnResultFragment.startForResult(intent, callback);
    }

    public void startForResult(Class<?> clazz, Callback callback) {
        Intent intent = new Intent(mAvoidOnResultFragment.getActivity(), clazz);
        startForResult(intent, callback);
    }

    public interface Callback {
        void onActivityResult(int resultCode, Intent data);
    }
}
