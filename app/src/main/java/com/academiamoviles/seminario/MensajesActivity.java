package com.academiamoviles.seminario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.academiamoviles.seminario.adapter.MensajesAdapter;
import com.academiamoviles.seminario.model.MensajeModel;

import java.util.ArrayList;
import java.util.List;

public class MensajesActivity extends AppCompatActivity {

    EditText etNombres, etMensaje;
    Button btnGrabar, btnEnviar;

    RecyclerView rvMensajes;

    List<MensajeModel> mLista = new ArrayList<>();
    MensajesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);

        initViews();
        setupRecyclerView();

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
    }
}
