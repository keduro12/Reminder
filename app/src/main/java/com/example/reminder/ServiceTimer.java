package com.example.reminder;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;

public class ServiceTimer extends Service {
    private CountDownTimer countDownTimer, countDownTimer2;
    private Vibrator vibrator;
    private boolean isVibrating = false;



    public ServiceTimer() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

    }

    private void updateInterface(boolean setTimerEnabled, boolean setTimerValueEnabled, boolean closeTimerEnabled, String timerText, boolean startTimer){
        Intent intent = new Intent("update_interface");
        intent.putExtra("setTimerEnabled", setTimerEnabled);
        intent.putExtra("setTimerValueEnabled", setTimerValueEnabled);
        intent.putExtra("closeTimerEnabled", closeTimerEnabled);
        intent.putExtra("timerText", timerText);
        intent.putExtra("startTimer", startTimer);

        sendBroadcast(intent);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int timerDuration = intent.getIntExtra("timerDuration", 0);

        if (!isVibrating){
            countDownTimer = new CountDownTimer(timerDuration, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String timerText = "Time restate: " + millisUntilFinished / 1000 + " seconds";
                    updateInterface(false, false, true, timerText, false);
                }

                @Override
                public void onFinish() {

                    if (vibrator.hasVibrator()){
                        vibrator.vibrate(10000);
                        isVibrating = true;
                    }

                    countDownTimer2 = new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            String timerText = "Time vibration: " + millisUntilFinished / 1000 + " seconds";
                            updateInterface(false, false, true, timerText, false);
                        }

                        @Override
                        public void onFinish() {
                            isVibrating = false;

                            if (!isVibrating) {
                                updateInterface(true, true, false, "Stopped timer", true);
                            }
                        }
                    };

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        }
                    }, 10000);
                    countDownTimer2.start();
                }

            };
            countDownTimer.start();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}