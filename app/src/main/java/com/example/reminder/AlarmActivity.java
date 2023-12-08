package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmActivity extends AppCompatActivity {

    private Button setTimer, closeTimer;
    private TextView timerView;
    private EditText setTimerValue;
    private CountDownTimer countDownTimer, countDownTimer2;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private Vibrator vibrator;
    private boolean isVibrating = false;
    private boolean isAlarmActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        IntentFilter filter = new IntentFilter("update_interface");
        registerReceiver(receiver, filter);

        setTimerValue = findViewById(R.id.edit_set_minutes);
        timerView = findViewById(R.id.txt_timer);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        setTimer = findViewById(R.id.button_set_timer);
        closeTimer = findViewById(R.id.button_close_timer);
        closeTimer.setEnabled(false);

/*      alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);*/

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean setTimerEnabled = intent.getBooleanExtra("setTimerEnabled", false);
            boolean setTimerValueEnabled = intent.getBooleanExtra("setTimerValueEnabled", false);
            boolean closeTimerEnabled = intent.getBooleanExtra("closeTimerEnabled", false);
            boolean startTimer = intent.getBooleanExtra("startTimer", false);

            setTimer.setEnabled(setTimerEnabled);
            setTimerValue.setEnabled(setTimerValueEnabled);
            closeTimer.setEnabled(closeTimerEnabled);

            String timerText = intent.getStringExtra("timerText");
            timerView.setText(timerText);

            if (startTimer) {
                startTimer(null);
            }

        }
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(receiver);
    }


    public void startTimer(View view){
        String inputText = setTimerValue.getText().toString();

        if (inputText.isEmpty()){
            Toast.makeText(this, "Fill the field", Toast.LENGTH_SHORT).show();
            return;
        }

        if (inputText.equals("0")){
            Toast.makeText(this, "You must enter a number greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent serviceIntent = new Intent(this, ServiceTimer.class);

        serviceIntent.putExtra("timerDuration", Integer.parseInt(inputText));

        startService(serviceIntent);



/*
        setTimer.setEnabled(false);
        setTimerValue.setEnabled(false);
        closeTimer.setEnabled(true);
*/

/*
        String valueEditText = setTimerValue.getText().toString();
        int converterMinutes = Integer.parseInt(valueEditText) * 10000;*/

    }

    public void closeTimer(View view){
        if (isVibrating){
            vibrator.cancel();
            countDownTimer2.cancel();
            isVibrating = false;
            setTimer.setEnabled(true);
            setTimerValue.setEnabled(true);
            timerView.setText("Stopped timer");
            closeTimer.setEnabled(false);
        }

        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
            setTimer.setEnabled(true);
            setTimerValue.setEnabled(true);
            timerView.setText("Stopped timer");
            closeTimer.setEnabled(false);
        }

    }


    /*public void startAlarm() {
*//*        if (alarmManager != null && alarmIntent != null) {
            alarmManager.cancel(alarmIntent);
        }

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);*//*
        Intent intent = new Intent(this, AlarmReceiver.class);

        alarmManager =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent =
                PendingIntent.getService(this, 3000, intent,
                        PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, alarmIntent);
    }*/


/*    public void cancelAlarm() {
        if (alarmManager != null && alarmIntent != null) {
            alarmManager.cancel(alarmIntent);
        }
    }*/

/*    private void activateAlarm() {
        if (!isAlarmActive) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 10000, alarmIntent);
            isAlarmActive = true;
        }
    }*/

}

