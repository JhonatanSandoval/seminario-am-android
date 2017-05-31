package com.academiamoviles.seminario.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketService extends Service {

    private static Socket mSocket;
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
        mSocket.on("nuevo_mensaje", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject datos = (JSONObject) args[0];
                // obtener los datos de args[0]

                try {
                    String nombres = datos.getString("nombres");
                    String mensaje = datos.getString("mensaje");


                    Intent iBroadcast = new Intent();
                    iBroadcast.putExtra("nombres", nombres);
                    iBroadcast.putExtra("mensaje", mensaje);

                    iBroadcast.setAction("NUEVO_MENSAJE");
                    sendBroadcast(iBroadcast);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                emitirEvento("listar_mensajes", null);
            }
        });
        mSocket.on("mensajes", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray array = (JSONArray) args[0];
                Log.i("SocketService", "array: " + array.toString());
            }
        });
    }

    public static void emitirEvento(String evento, JSONObject json) {
        if (mSocket.connected()) {
            mSocket.emit(evento, json);
        }
    }

    public SocketService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
