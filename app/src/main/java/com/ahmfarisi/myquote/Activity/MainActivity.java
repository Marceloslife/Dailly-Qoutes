package com.ahmfarisi.myquote.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ahmfarisi.myquote.API.APIRequestData;
import com.ahmfarisi.myquote.API.RetroServer;
import com.ahmfarisi.myquote.Adapter.AdapterQuote;
import com.ahmfarisi.myquote.Model.QuoteModel;
import com.ahmfarisi.myquote.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvQoute;
    private ProgressBar pbQoute;
    private List<QuoteModel> listQuote;
    private AdapterQuote adQuote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvQoute = findViewById(R.id.rv_quote);
        pbQoute = findViewById(R.id.pb_quote);
        rvQoute.setLayoutManager(new LinearLayoutManager(this));
        retrieveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    private void retrieveData(){
        pbQoute.setVisibility(View.VISIBLE);
        APIRequestData ARD = RetroServer.connectRetrofit().create(APIRequestData.class);
        Call<List<QuoteModel>> retrieveProcess = ARD.ardRetrieve();

        retrieveProcess.enqueue(new Callback<List<QuoteModel>>() {
            @Override
            public void onResponse(Call<List<QuoteModel>> call, Response<List<QuoteModel>> response) {
                listQuote = response.body();
                adQuote = new AdapterQuote(listQuote, MainActivity.this);
                rvQoute.setAdapter(adQuote);
                pbQoute.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<QuoteModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Connection Error! Cannot Reach Server", Toast.LENGTH_SHORT).show();
                pbQoute.setVisibility(View.GONE);
            }
        });

    }
}