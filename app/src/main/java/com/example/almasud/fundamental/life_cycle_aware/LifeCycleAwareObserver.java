package com.example.almasud.fundamental.life_cycle_aware;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Life cycle observer class: In life cycle observer ON_CREATE to ON_RESUME states
 * are triggered after the activity's OnCreate to OnResume respectively but ON_PAUSE
 * to ON_DESTROY states are triggered before the activity's onPause to onDestroy
 * respectively. Thus Observer guarantee to us end of activity's events.
 */

public class LifeCycleAwareObserver implements LifecycleObserver {
    private String TAG = this.getClass().getSimpleName();
    private Context context;

    public LifeCycleAwareObserver(Context context) {
        this.context = context;
    }

    // For using OnLifecycleEvent annotation methods are automatically call when
    // the specific event triggered in Activity
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreateEvent() {
        Log.i(TAG, "Observer ON_CREATE");
        Toast.makeText(context, TAG + "Observer ON_CREATE", Toast.LENGTH_SHORT).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStartEvent() {
        Log.i(TAG, "Observer ON_START");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResumeEvent() {
        Log.i(TAG, "Observer ON_RESUME");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPauseEvent() {
        Log.i(TAG, "Observer ON_PAUSE");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStopEvent() {
        Log.i(TAG, "Observer ON_STOP");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroyEvent() {
        Log.i(TAG, "Observer ON_DESTROY");
        Toast.makeText(context, TAG + "Observer ON_DESTROY", Toast.LENGTH_SHORT).show();
    }
}
