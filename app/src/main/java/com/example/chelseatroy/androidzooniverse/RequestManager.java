package com.example.chelseatroy.androidzooniverse;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestManager {
    private static RequestManager sInstance;
    private RequestQueue mRequestQueue;
    private Context mContext;

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public static RequestManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RequestManager(context.getApplicationContext());
        }
        return sInstance;
    }

    private RequestManager(Context context) {
        mContext = context;
    }
}
