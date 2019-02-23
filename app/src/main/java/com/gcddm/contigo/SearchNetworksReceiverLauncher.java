package com.gcddm.contigo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by Gilbert on 5/12/2017.
 */
public class SearchNetworksReceiverLauncher extends ContextWrapper {
    static private SearchNetworksReceiverLauncher _instance = null;
    private PendingIntent mAlarmIntent = null;
    private AlarmManager manager = null;
    private Context context = null;
    private long interval = 20000;
    private boolean launched = false;

    private SearchNetworksReceiverLauncher(final Context c)
    {
        super(c);
        context = c;
    }

    static public SearchNetworksReceiverLauncher getInstance(final Context c)
    {
        if(_instance == null)
            _instance = new SearchNetworksReceiverLauncher(c);
        return _instance;
    }

    public void launch()
    {
        launched = true;
        Intent launchIntent = new Intent(context, SearchNetworksReceiver.class);
        mAlarmIntent = PendingIntent.getBroadcast(context, 0, launchIntent, 0);
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 5000, interval, mAlarmIntent);
    }

    public void cancel()
    {
        launched = false;
        manager.cancel(mAlarmIntent);
    }

    public boolean hasLaunched()
    {
        return launched;
    }
}
