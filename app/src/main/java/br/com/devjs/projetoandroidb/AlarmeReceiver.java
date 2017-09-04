package br.com.devjs.projetoandroidb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Não esquecer de informar seu peso!", Toast.LENGTH_SHORT).show();
    }
}
