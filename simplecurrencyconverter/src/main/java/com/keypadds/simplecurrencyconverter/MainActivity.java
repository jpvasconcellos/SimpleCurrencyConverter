package com.keypadds.simplecurrencyconverter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.keypadds.simplecurrencyconverter.adapter.RatesAdapter;
import com.keypadds.simplecurrencyconverter.model.CurrencyExchangeRate;
import com.keypadds.simplecurrencyconverter.util.RequestSingleton;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView rvRates;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            performVolleyRequest();

            rvRates = (RecyclerView) findViewById(R.id.rvRates);

            rvRates.setHasFixedSize(true);
            rvLayoutManager = new LinearLayoutManager(this);
            rvRates.setLayoutManager(rvLayoutManager);
    }

    private void performVolleyRequest() {
        String fixerBaseUrl = "http://api.fixer.io/latest?base=BRL ";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                fixerBaseUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response != null) {
                            parseResponse(response);
                        } else {
                            showNetWorkErrorDialog();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showNetWorkErrorDialog();
                    }
                }
        );

        jsonObjectRequest.setTag(TAG);

        RequestSingleton
                .getInstance(this.getApplicationContext())
                .addToRequestQueue(jsonObjectRequest);
    }

    // Optei pro usar JSONObjects mesmo, em vez de mapear objetos com Gson
    // como a API não retorna um array de símbolos, o parse seria complicado mesmo com Gson
    private void parseResponse(JSONObject response) {
        String baseCurrency = response.optString("base");
        JSONObject jsonRates = response.optJSONObject("rates");
        JSONArray jsonNames = jsonRates.names();

        if(jsonNames.length() < 1) {
            showNetWorkErrorDialog();

        } else {
            JSONObject item;
            int max = jsonNames.length();
            CurrencyExchangeRate cer[] = new CurrencyExchangeRate[max];

            for(int i = 0; i < max; i++) {
                String currency = jsonNames.optString(i);
                CurrencyExchangeRate newCer = new CurrencyExchangeRate(
                        currency,
                        jsonRates.optDouble(currency),
                        getResources().getIdentifier("flag_" + currency.toLowerCase(), "drawable", getPackageName()),
                        getString(getResources().getIdentifier(currency, "string", getPackageName()))
                );

                cer[i] = newCer;
            }

            rvAdapter = new RatesAdapter(cer, baseCurrency, this);
            rvRates.setAdapter(rvAdapter);
        }

    }

    private void showNetWorkErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.network_error_title)
                .setMessage(R.string.network_error_message)
                .setPositiveButton(R.string.network_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        performVolleyRequest();
                    }
                })
                .setNegativeButton(R.string.network_dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestSingleton.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll(TAG);
    }

}
