package com.gavilan.serviciosenandroid;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyFirebaseService extends Service {

    FirebaseDatabase database;
    DatabaseReference myRef;

    String rut = "";

    final static String CHANEL_ID = "FIREBASE CHANEL";
    final static int NOTIFICACION_ID = 0;

    public MyFirebaseService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        rut = MainActivity.rut;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("despachos");
        crearCanalNotificacion();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        myRef.child(rut).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Despacho miDespacho = snapshot.getValue(Despacho.class);
                if( miDespacho.transito == true ){
                    //Lanzar notificación
                    crearNotificación();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return super.onStartCommand(intent, flags, startId);
    }

    public void crearCanalNotificacion(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notificaciones firebase";
            NotificationChannel channel =
                    new NotificationChannel(CHANEL_ID,name, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void crearNotificación(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANEL_ID);
        builder.setSmallIcon(android.R.drawable.arrow_down_float);
        builder.setContentTitle("Atención");
        builder.setContentText("Tú pedido está en camino!");
        builder.setColor(Color.RED);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }
}