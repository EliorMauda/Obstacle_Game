package com.example.netflixmoviesapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.netflixmoviesapp.R;
import com.example.netflixmoviesapp.adapter.FilmListAdapter;
import com.example.netflixmoviesapp.domain.FilmItem;
import com.example.netflixmoviesapp.domain.ListFilm;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterNewMovies, adapterUpComing;
    private RecyclerView recyclerViewNewMovies, recyclerViewUpComing;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2;
    private ProgressBar loading1, loading2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        sendRequest1();
        sendRequest2();
    }

    private void sendRequest1() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading1.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                loading1.setVisibility(View.GONE);

                ListFilm items = gson.fromJson(response , ListFilm.class);
                adapterNewMovies = new FilmListAdapter(items);
                recyclerViewNewMovies.setAdapter(adapterNewMovies);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("uilover" , "sendRequest1: " + error.toString());
                loading1.setVisibility(View.GONE);
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    private void sendRequest2() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading2.setVisibility(View.VISIBLE);
        mStringRequest2 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=3", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                loading2.setVisibility(View.GONE);

                ListFilm items = gson.fromJson(response , ListFilm.class);
                adapterUpComing = new FilmListAdapter(items);
                recyclerViewUpComing.setAdapter(adapterUpComing);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading2.setVisibility(View.GONE);
            }
        });
        mRequestQueue.add(mStringRequest2);
    }

    private void initView() {
        recyclerViewNewMovies = findViewById(R.id.view1);
        recyclerViewNewMovies.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));
        recyclerViewUpComing = findViewById(R.id.view2);
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));
        loading1 = findViewById(R.id.loading1);
        loading2 = findViewById(R.id.loading2);
    }
}