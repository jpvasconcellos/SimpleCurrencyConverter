package com.keypadds.simplecurrencyconverter.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

/**
 * Created by vasconcellos on 22/08/17.
 */

public class CurrencyExchangeRate implements Parcelable {

    private String currencyCode;
    private double exchangeRate;
    private int countryFlag;
    private String currencyName;

    public CurrencyExchangeRate () {

    }

    public CurrencyExchangeRate(String currencyCode, double exchangeRate, int countryFlag, String currencyName) {
        this.currencyCode = currencyCode;
        this.exchangeRate = exchangeRate;
        this.countryFlag = countryFlag;
        this.currencyName = currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public int getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(int countryFlag) {
        this.countryFlag = countryFlag;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getFormattedCurrencyName() {
        return String.format(Locale.getDefault(), "%s (%s)", currencyName, currencyCode);
    }

    public String getFormattedExchangeRate(String baseCurrency) {
        return String.format(Locale.getDefault(), "1 %s = %f %s", baseCurrency, exchangeRate, currencyCode);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.currencyCode);
        dest.writeDouble(this.exchangeRate);
        dest.writeInt(this.countryFlag);
        dest.writeString(this.currencyName);
    }

    protected CurrencyExchangeRate(Parcel in) {
        this.currencyCode = in.readString();
        this.exchangeRate = in.readDouble();
        this.countryFlag = in.readInt();
        this.currencyName = in.readString();
    }

    public static final Parcelable.Creator<CurrencyExchangeRate> CREATOR = new Parcelable.Creator<CurrencyExchangeRate>() {
        @Override
        public CurrencyExchangeRate createFromParcel(Parcel source) {
            return new CurrencyExchangeRate(source);
        }

        @Override
        public CurrencyExchangeRate[] newArray(int size) {
            return new CurrencyExchangeRate[size];
        }
    };
}
