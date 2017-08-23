package com.keypadds.simplecurrencyconverter.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestSingleton {
    /* Apesar do android Studio reclamar, isso aqui não gera vazamento de memória.
     * O vazamento acontece quando vc guarda uma referência estática que aponta para
     * um Activity Context. Como a Activity é destruída e recriada em diversos momentos,
     * como quando o dispositivo é rotacionado, essa referência quebraria o App. Entretanto,
     * nesse caso, estamos guardando uma referência estática ao Application Context, que não
     * segue o lifecycle e, por isso, permite uqe as requests sejam mantidas mesmo em momentos
     * aonde o Activity Context seria destruído
     */
    private static RequestSingleton mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;

    private RequestSingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized RequestSingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestSingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // E é aqui que a gente pega o context correto para evitar vazamentos de memória
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}