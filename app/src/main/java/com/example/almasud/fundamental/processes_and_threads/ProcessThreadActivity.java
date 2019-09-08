package com.example.almasud.fundamental.processes_and_threads;

import android.os.AsyncTask;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.almasud.fundamental.R;

/**
 * Everything is happening in the UI thread (also called "main" thread),
 * performing long operations such as network access or database queries will
 * block the whole UI. When the thread is blocked, no events can be dispatched,
 * including drawing events. From the user's perspective, the application
 * appears to hang. Even worse, if the UI thread is blocked for more than a
 * few seconds (about 5 seconds currently) the user is presented with the
 * infamous "application not responding" (ANR) dialog. The user might then
 * decide to quit your application and uninstall it if they are unhappy.
 * Additionally, the Android UI toolkit is not thread-safe. So, you must not
 * manipulate your UI from a worker thread ("background" thread)—you must do
 * all manipulation to your user interface from the UI thread. Thus, there
 * are simply two rules to Android's single thread model:
 *
 *    1. Do not block the UI thread
 *    2. Do not access the Android UI toolkit from outside the UI thread
 *
 * If you have operations to perform that are not instantaneous, you should
 * make sure to do them in separate threads ("background" or "worker" threads).
 * However, note that you cannot update the UI from any thread other than the
 * UI thread or the "main" thread. To fix this problem, Android offers several
 * ways to access the UI thread from other threads.
 */

public class ProcessThreadActivity extends AppCompatActivity {
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_thread);

        mProgressBar = findViewById(R.id.threadPBar);
    }

    public void longProcessMainThread(View view) {
        try {
            // Application may be hanged by long process that blocks the UI or main thread.
            Thread.sleep(10000);
            Toast.makeText(this, "Long process has been finished in UI or main thread.", Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateUIOtherThread(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /* We cannot update the UI from any thread other than the
                 * UI thread or the "main" thread. */
                Toast.makeText(ProcessThreadActivity.this, "Execute Toast UI in another thread.", Toast.LENGTH_LONG).show();
            }
        }).start();
        Toast.makeText(ProcessThreadActivity.this, "Execute in main thread.", Toast.LENGTH_LONG).show();
    }

    public void updateUIByHandler(View view) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                /* Handler post the UI component in main or UI thread. It is useful
                   only after execution of worker or background thread. But it is
                   difficult to manage when try to communicate between main and
                   background thread frequently (i.e. show download progress in main
                   thread that executing in background thread). The best solution is
                   to extend the AsyncTask class, which simplifies the execution of
                   worker thread tasks that need to interact with the UI.
                 */
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProcessThreadActivity.this, "Execute Toast UI in another thread by handler.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }

    // Extending AsyncTask class without creating any thread.
    public void extendingAsyncTask(View view) {
        new ProgressTask().execute();
    }

    private class ProgressTask extends AsyncTask<Void, Integer, Void> {
        /** This step is used to perform background
         * computation that can take a long time.
         */
        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*
                  To publish one or more units of progress.
                  These values are published on the UI thread, in the
                  onProgressUpdate(Progress...) step.
                 */
                publishProgress(i);
            }
            return null;
        }

        /**
         *  This method is used to display any form of progress in the user
         *  interface while the background computation is still executing.
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressBar.setProgress(values[0]);
        }

        /**
         * The result of the background computation is passed to this step
         * as a parameter.
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(ProcessThreadActivity.this, "Progress has been finished.", Toast.LENGTH_LONG).show();
        }
    }
}
