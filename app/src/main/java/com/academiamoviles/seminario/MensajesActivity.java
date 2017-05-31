package com.academiamoviles.seminario;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.academiamoviles.seminario.adapter.MensajesAdapter;
import com.academiamoviles.seminario.model.MensajeModel;
import com.academiamoviles.seminario.services.GeolocationService;
import com.academiamoviles.seminario.services.SocketService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MensajesActivity extends AppCompatActivity {

    EditText etNombres, etMensaje;
    Button btnGrabar, btnEnviar;

    RecyclerView rvMensajes;

    List<MensajeModel> mLista = new ArrayList<>();
    MensajesAdapter adapter;


    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            switch (action) {
                case "NUEVO_MENSAJE":

                    // insertar el mensaje en la lista (mLista)
                    String nombres = intent.getStringExtra("nombres");
                    String mensaje = intent.getStringExtra("mensaje");

                    MensajeModel obj = new MensajeModel();
                    obj.setNombres(nombres);
                    obj.setMensaje(mensaje);

                    mLista.add(obj);

                    cargarListaMensajes();

                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);

        // iniciar el servicio
        Intent servicioIntent = new Intent(MensajesActivity.this, GeolocationService.class);
        startService(servicioIntent);


        Intent servicioSocket = new Intent(MensajesActivity.this, SocketService.class);
        startService(servicioSocket);

        initViews();
        setupRecyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // registrar el broadcastreceiver

        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("NUEVO_MENSAJE");
        mIntentFilter.addAction("OTRO_MENSAJE");
        mIntentFilter.addAction("LISTA_MENSAJES");

        registerReceiver(mBroadcastReceiver, mIntentFilter);

    }

    private void cargarListaMensajes() {
        adapter = new MensajesAdapter(mLista);
        rvMensajes.setAdapter(adapter);
    }

    private void setupRecyclerView() {
        rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MensajesActivity.this);
        rvMensajes.setLayoutManager(layoutManager);
    }

    private void initViews() {
        etNombres = (EditText) findViewById(R.id.etNombres);
        etMensaje = (EditText) findViewById(R.id.etMensaje);

        btnGrabar = (Button) findViewById(R.id.btnGrabar);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombres = etNombres.getText().toString();
                String mensaje = etMensaje.getText().toString();

                if (!nombres.isEmpty() && !mensaje.isEmpty()) {

                    // emitir al servidor

                    JSONObject json = new JSONObject();
                    try {
                        json.put("nombres", nombres);
                        json.put("mensaje", mensaje);

                        SocketService.emitirEvento("enviar_mensaje", json);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }

            }
        });


    }
}
