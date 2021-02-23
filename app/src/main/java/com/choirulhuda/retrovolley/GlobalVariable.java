package com.choirulhuda.retrovolley;

import android.content.Context;

public class GlobalVariable {

    private Context context;
    public static String BASE_URL = "http://192.168.43.51/volley/";
    public static String TYPE_CONN = "typeConnection";
    public static String RETROFIT = "retrofit";
    public static String VOLLEY = "volley";

    public GlobalVariable() {
    }

    public GlobalVariable(Context context) {
        this.context = context;
    }
}
