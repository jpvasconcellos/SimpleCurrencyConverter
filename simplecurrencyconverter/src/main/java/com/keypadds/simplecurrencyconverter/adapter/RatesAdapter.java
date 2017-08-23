package com.keypadds.simplecurrencyconverter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keypadds.simplecurrencyconverter.ConverterActivity;
import com.keypadds.simplecurrencyconverter.R;
import com.keypadds.simplecurrencyconverter.model.CurrencyExchangeRate;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.ViewHolder> {
    private CurrencyExchangeRate[] mDataset;
    private String mBaseCurrency;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCurrencyName;
        TextView tvExchangeRate;
        ImageView ivCountryFlag;

        public ViewHolder(View v) {
            super(v);
        }
    }

    public RatesAdapter(CurrencyExchangeRate[] myDataset, String baseCurrency, Context ctx) {
        mDataset = myDataset;
        mBaseCurrency = baseCurrency;
        mContext = ctx;
    }


    @Override
    public RatesAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rates_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);

        vh.ivCountryFlag = (ImageView) v.findViewById(R.id.ivCountryFlag);
        vh.tvCurrencyName = (TextView) v.findViewById(R.id.tvCurrencyName);
        vh.tvExchangeRate = (TextView) v.findViewById(R.id.tvExchangeRate);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CurrencyExchangeRate cer = mDataset[position];

        holder.ivCountryFlag.setImageResource(cer.getCountryFlag());
        holder.tvCurrencyName.setText(cer.getFormattedCurrencyName());
        holder.tvExchangeRate.setText(cer.getFormattedExchangeRate(mBaseCurrency));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, ConverterActivity.class);
                i.putExtra("CER", mDataset[position]);
                i.putExtra("BASE_CURRENCY", mBaseCurrency);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
