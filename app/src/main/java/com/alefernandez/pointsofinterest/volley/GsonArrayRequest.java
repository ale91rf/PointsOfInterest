package com.alefernandez.pointsofinterest.volley;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by alejandro on 19/06/2015.
 */
public class GsonArrayRequest<T> extends Request<T> {

    private final Response.Listener<T> listener;
    private final Gson gson;
    private final Type type;

    public GsonArrayRequest(int method, String url, Type type,
                            Response.Listener<T> listener, Response.ErrorListener errorListener, Gson gson) {
        super(method, url, errorListener);
        this.type = type;
        this.listener = listener;
        this.gson = gson;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            // The JSON string is obtained from the response (with appropriate charset).
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            // the JSON string is processed
            T datos = gson.fromJson(json, type);
            // response is created and returned
            return Response.success(datos,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    // Send the answer to the listener
    @Override
    protected void deliverResponse(T response) {
        // OnResponse method is called passing the response of the listener.
        listener.onResponse(response);
    }

}