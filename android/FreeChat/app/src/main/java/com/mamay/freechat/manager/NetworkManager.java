package com.mamay.freechat.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mamay.freechat.model.ChangedText;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Helps the app to connect itself to the network and to it's backend.
 */
public class NetworkManager {

    private Context context;
    private NetworkInfo networkInfo;
    private ConnectivityManager cm;

    public NetworkManager(Context context) {
        this.context = context;
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isOnline() {
        return cm.getActiveNetworkInfo().isConnected();
    }

    public boolean commitTextChanges(long chatId, ChangedText changedText) {
        return isOnline();


    }

    public JSONObject request(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        JSONObject object = null;

        try {
            object = readInput(connection.getInputStream());
        } finally {
            connection.disconnect();
        }

        return object;
    }

    public JSONObject readInput(InputStream i) {
        return null;
    }
}
