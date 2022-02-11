package com.example.pasarela_pago;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context econtext;
    private ArrayList<Frutas> elista;


    public Adapter(Context contex, ArrayList<Frutas> lusuario) {
        this.econtext = contex;
        this.elista = lusuario;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(econtext);
        View v = inflater.inflate(R.layout.activity_producto, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Frutas actual = elista.get(position);
        String url = actual.getImag();

        holder.mNombre.setText("Nombre: " + actual.getNombre());
        holder.mprecio.setText(String.valueOf(actual.getValor()));
        holder.cantidad.setText(String.valueOf(actual.getCantidad()));
        holder.mtotal.setText(String.valueOf(actual.getPrecioFinal()));
        Picasso.with(econtext)
                .load(url)
                .into(holder.imag);


    }

    @Override
    public int getItemCount() {
        return elista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mNombre, mprecio,mtotal,cantidad;
        public ImageView imag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mNombre = itemView.findViewById(R.id.lblName);
            mprecio = itemView.findViewById(R.id.lblTotal2);
            imag = itemView.findViewById(R.id.imageView);
            cantidad = itemView.findViewById(R.id.lblCantidad);
            mtotal = itemView.findViewById(R.id.lblPTotal);

        }
    }
}
