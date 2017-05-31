package com.academiamoviles.seminario.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class SocketService extends Service {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.1.22:9000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mSocket.connect();
        mSocket.on("mensaje_bienvenida", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.i("SocketService", "datos recibidos");
            }
        });

    }

    public SocketService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
