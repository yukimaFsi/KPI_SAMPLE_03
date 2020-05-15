package jp.inotou.android.kpi_sample_03.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartupReceiver extends BroadcastReceiver {
    private static final String LOGC = "StartupReceiver:";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(LOGC,"Start Now");
        //Intent newIntent=new Intent("jp.gihyo.exercise.carePlan.CARE_PLAN_CHECK");
        Intent newIntent=new Intent("jp.inotou.android.kpi_sample_03.ActivityRegist");
        context.startService(newIntent);
    }
}
