package com.cferndp.bandnotification;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;

public class NLService extends NotificationListenerService {
        Context context;

        private final String commandVibrate = "am broadcast -a android.provider.Telephony.SMS_RECEIVED";

        @Override
        public void onCreate() {

            super.onCreate();
            context = getApplicationContext();

        }

        protected void vibrateBand() {
            try {
                Runtime.getRuntime().exec("su -c " + commandVibrate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        protected boolean isPackageSuscribe(String pack) {
            pack = pack.toLowerCase();

            if(pack.contains("telegram") || pack.contains("whatsapp")){
                return true;
            }else{
                return false;
            }
        }

        @Override
        public void onNotificationPosted(StatusBarNotification sbn) {

            String pack = sbn.getPackageName().toLowerCase();

            String text = "";
            String title = "";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                Bundle extras = extras = sbn.getNotification().extras;
                text = extras.getCharSequence("android.text").toString();
                title = extras.getString("android.title");
            }

            if(isPackageSuscribe(pack)){
                vibrateBand();
            }



            Log.i("Package",pack);
            Log.i("Title",title);
            Log.i("Text",text);

        }

        @Override
        public void onNotificationRemoved(StatusBarNotification sbn) {
            Log.i("Msg","Notification was removed");
        }
    }