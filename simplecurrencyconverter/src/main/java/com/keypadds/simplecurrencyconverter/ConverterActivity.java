package com.keypadds.simplecurrencyconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.keypadds.simplecurrencyconverter.model.CurrencyExchangeRate;

import java.util.Locale;

public class ConverterActivity extends AppCompatActivity {
    private static final String TAG = ConverterActivity.class.getSimpleName();
    Double baseValue;
    Double exchangeRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        CurrencyExchangeRate cer = getIntent().getParcelableExtra("CER");
        String baseCurrency = getIntent().getStringExtra("BASE_CURRENCY");

        exchangeRate = cer.getExchangeRate();

        EditText etBaseCurrency = (EditText) findViewById(R.id.etBaseCurrency);
        TextView tvBaseCurrency = (TextView) findViewById(R.id.tvBaseCurrency);
        final TextView tvTargetCurrencyValue = (TextView) findViewById(R.id.tvTargetCurrencyValue);
        TextView tvTargetCurrency = (TextView) findViewById(R.id.tvTargetCurrency);
        TextView tvRate = (TextView) findViewById(R.id.tvRate);

        tvRate.setText(cer.getFormattedExchangeRate(baseCurrency));
        tvBaseCurrency.setText(baseCurrency);
        tvTargetCurrency.setText(cer.getCurrencyCode());

        etBaseCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    baseValue = Double.parseDouble(editable.toString());
                } catch (Exception e) { // Não importa o que houve, só importa que não é um Double
                    Log.e(TAG, e.getMessage(), e);
                    baseValue = 0.0d;
                }

                tvTargetCurrencyValue.setText(String.format(Locale.getDefault(), "%.02f", baseValue * exchangeRate));
            }
        });

    }
}
