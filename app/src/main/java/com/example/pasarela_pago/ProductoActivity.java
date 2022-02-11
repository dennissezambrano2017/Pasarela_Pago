package com.example.pasarela_pago;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductoActivity extends Fragment {

    private RecyclerView lstFrutas;
    private Frutas frutas;
    private ArrayList<Frutas> list_Fru;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_item, container, false);
        frutas = new Frutas();
        list_Fru = new ArrayList<>();
        lstFrutas = (RecyclerView) rootView.findViewById(R.id.lstFrutas);
        lstFrutas.setLayoutManager(new LinearLayoutManager(getContext()));
        requestQueue = Volley.newRequestQueue(getContext());
        stringRequest();
        return rootView;
    }


    public void stringRequest() {

        String URL = "https://my-json-server.typicode.com/dennissezambrano2017/demo_json/db";
        StringRequest request = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("fruteria");

                            list_Fru = frutas.JsonObjectsBuild(jsonArray);
                            Adapter adapter = new Adapter(getActivity().getApplicationContext(), list_Fru);

                            lstFrutas.setAdapter(adapter);


                        } catch (JSONException e) {
                            Log.d("Error: ", e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

}