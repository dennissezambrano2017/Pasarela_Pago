package com.example.pasarela_pago;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class CarritoActivity extends Fragment {

    public ImageView imag;
    TextView txttotal;
    EditText txtdni;
    EditText txtcodepais;
    EditText txtTelefon;
    private final String TOKEN = "gJsnfekxexMD8spVr4fTOHSFO-QPlRSycuOAEjLSzVTrUSb6AlHZPRqCFvm3VSWimbAw4EaMkWRlPdNa6fIZj3Uphd2lX1ztBGG52eKgi-wKlcXCchnFhBWSHtEUhxgf3Fo1lKaPfbBUXzekLVH0sTLXxUjUMaMAAlyiiSm0l1xjDsVvT66JIaT2cklotBU12fStVZT_qbVNu3z6WE5tcYPX30DuqBj-LLo07iAoqwm-W60kB6lihoC8lwnoXV4Gdod6RRr7G5H47O8Dw-7gOkGc7IVHsX-1cj9OPNjTq0Dado47-jbkLTszlUZWMoHhTb1udg";

    ProductoActivity productoActivity = new ProductoActivity();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_carrito, container, false);
        String url = "https://png.pngtree.com/png-vector/20200407/ourlarge/pngtree-shopping-bags-under-online-store-concept-png-image_2177918.jpg";
        imag = rootView.findViewById(R.id.tienda);
        Picasso.with(getContext())
                .load(url)
                .into(imag);
        txttotal = (TextView) rootView.findViewById(R.id.txt_Price);
        txtdni = (EditText) rootView.findViewById(R.id.txt_Dni);
        txtcodepais = (EditText) rootView.findViewById(R.id.txt_Codi);
        txtTelefon = (EditText) rootView.findViewById(R.id.txt_Telefono);

        Button buton = (Button) rootView.findViewById(R.id.button2);

        buton.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {

                        try {
                            llenarJson();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );

        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void llenarJson() throws JSONException {
        //valida el monto
        int monto;
        try {
            Double pago = 1.0;//Double.valueOf(txttotal.getText().toString());
            pago = pago * 100;
            DecimalFormat dformat = new DecimalFormat("#");
            monto = Integer.parseInt(dformat.format(pago));
        } catch (Exception e) {
            monto = 110;
            Log.i("Error: ", e.getMessage());
        }
        Log.i("Deposito: ", String.valueOf(monto));
        int tax = (int) (monto * 0.06);
        Log.i("Tax: ", String.valueOf(tax));
        try {

            if (txtcodepais.getText().toString().trim().length() > 0 &&
                    txtTelefon.getText().toString().trim().length() > 0 &&
                    txtdni.getText().toString().trim().length() > 0) {

                JSONObject json = new JSONObject();

                json.put("phoneNumber", txtTelefon.getText().toString());
                json.put("countryCode", txtcodepais.getText().toString());
                json.put("clientUserId", txtdni.getText().toString());
                json.put("responseUrl", "http://paystoreCZ.com/confirm.php");
                json.put("amount", monto);
                json.put("amountWithTax", monto - tax);
                json.put("amountWithoutTax", 0);
                json.put("tax", tax);
                json.put("clientTransactionId", UUID.randomUUID().toString().toUpperCase().subSequence(0, 6));

                Log.i("Json", json.toString());
                Toast toast = Toast.makeText(getContext(), "pagando", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                realizarDonacion(json);
            } else {
                Toast.makeText(getContext(), "Necesita ingresar todos los datos..", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void realizarDonacion(JSONObject data) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest pagarPayPhone = new JsonObjectRequest(
                Request.Method.POST,
                "https://pay.payphonetodoesposible.com/api/Sale",
                data,// info del pago
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("transactionId: " + response.getInt("transactionId"));
                            Toast.makeText(getContext(), "Solicitud de pago enviada: " + response.getInt("transactionId"), Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + TOKEN);
                Log.d("Params",params.toString());
                return params;
            }
        };

        queue.add(pagarPayPhone);
    }
}
