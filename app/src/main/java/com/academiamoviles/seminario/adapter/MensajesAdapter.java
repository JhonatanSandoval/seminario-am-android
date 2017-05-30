package com.academiamoviles.seminario.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.academiamoviles.seminario.R;
import com.academiamoviles.seminario.model.MensajeModel;

import java.util.List;

public class MensajesAdapter extends RecyclerView.Adapter<MensajesAdapter.MensajeHolder> {

    private List<MensajeModel> mListaMensajes;

    public MensajesAdapter(List<MensajeModel> listaMensajes) {
        mListaMensajes = listaMensajes;
    }

    @Override
    public MensajeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MensajeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje, parent, false));
    }

    @Override
    public void onBindViewHolder(MensajeHolder holder, int position) {
        holder.getTvMensaje().setText(mListaMensajes.get(position).getMensaje());
        holder.getTvNombres().setText(mListaMensajes.get(position).getNombres());
    }

    @Override
    public int getItemCount() {
        return mListaMensajes.size();
    }

    class MensajeHolder extends RecyclerView.ViewHolder {

        private TextView tvMensaje, tvNombres;

        public MensajeHolder(View itemView) {
            super(itemView);
            tvMensaje = (TextView) itemView.findViewById(R.id.tvMensaje);
            tvNombres = (TextView) itemView.findViewById(R.id.tvNombres);
        }

        public TextView getTvMensaje() {
            return tvMensaje;
        }

        public TextView getTvNombres() {
            return tvNombres;
        }
    }

}
