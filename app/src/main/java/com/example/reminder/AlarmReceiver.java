package com.example.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context, "¡Alarma activada!", Toast.LENGTH_SHORT).show();
        playAlarmSound(context);

    }

    private void playAlarmSound(Context context) {
        try {
            // Obtener el sonido de la alarma del sistema
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

            // Crear un objeto Ringtone
            Ringtone ringtone = RingtoneManager.getRingtone(context, alarmSound);

            // Reproducir el sonido
            if (ringtone != null) {
                ringtone.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
