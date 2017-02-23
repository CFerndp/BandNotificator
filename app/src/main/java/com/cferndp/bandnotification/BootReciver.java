package com.cferndp.bandnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReciver extends BroadcastReceiver {
    public BootReciver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notservice = new Intent(context,NLService.class);
        context.startService(notservice);
    }
}
