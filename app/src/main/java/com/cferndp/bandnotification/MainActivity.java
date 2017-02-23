package com.cferndp.bandnotification;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_NOTIFY = 69 ;
    private final String commandVibrate = "am broadcast -a android.provider.Telephony.SMS_RECEIVED";

    EditText command;
    EditText commandSU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        command = (EditText) findViewById(R.id.commandText);
        commandSU = (EditText) findViewById(R.id.suOptText);
        Button btn = (Button) findViewById(R.id.button);
        checkAndrequestNotPerms();

        startService(new Intent(this,NLService.class));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = command.getText().toString();
                String su = commandSU.getText().toString();

                String cmdF = su + " " +cmd;

                Toast.makeText(getApplicationContext(),"Ejecutando: " + cmdF,Toast.LENGTH_SHORT).show();
                execute(cmdF);
            }
        });

    }

    private void execute(String command){
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void checkAndrequestNotPerms() {
        String notificationListenerString = Settings.Secure.getString(this.getContentResolver(),"enabled_notification_listeners");
        if (notificationListenerString == null || !notificationListenerString.contains(getPackageName())) {
            //The notification access has not acquired yet!
            Log.d("App", "no access");
            requestPermission();
        }
        else {
            //Your application has access to the notifications
            Log.d("App", "has access");
        }
    }

    public void requestPermission() {
        Intent requestIntent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        requestIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(requestIntent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
