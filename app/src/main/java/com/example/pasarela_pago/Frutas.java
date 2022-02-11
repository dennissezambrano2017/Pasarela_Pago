package com.example.pasarela_pago;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Frutas {
    private String nombre;
    private double valor;
    private String imag;
    private int cantidad;
    private double precioFinal;

    public Frutas() {
    }

    public Frutas(String nombre, double valor, int cantidad, double precioFinal, String imag) {
        this.nombre = nombre;
        this.valor = valor;
        this.imag = imag;
        this.cantidad = cantidad;
        this.precioFinal = precioFinal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getImag() {
        return imag;
    }

    public void setImag(String imag) {
        this.imag = imag;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public static ArrayList<Frutas> JsonObjectsBuild(JSONArray datos) throws JSONException {
        ArrayList<Frutas> frutas = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            JSONObject user=  datos.getJSONObject(i);
            Log.d("DATOS", user.toString());
            frutas.add(new Frutas(user.getString("Name"),
                    user.getDouble("precie"),
                    user.getInt("cantidad"),
                    user.getDouble("total"),
                    user.getString("imag")));
        }
        return frutas;
    }
}
